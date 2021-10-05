'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');


var stompClient = null;

var username = null;
var idChat = null;
var register; // salva o subscribe para receber a mensagem de volta, e após isso faz o unsubscribe para não receber se o mesmo usuário entrar em outra sala
var privateMessage = null;
var listOfParticipants = null;
var chatTitle=null;
var conectou = false;

// private long idUser;
//     private long chatID;
//     private int maxParticipants;
//     private Set<Long> listOfParticipants= new Set<>();

// var ChatMessageRegistry={
// idUser: username,
// chatID:idChat,
// chatTitle:chatTitle,
// type: 'JOIN'
// }


var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
console.log('Iniciando conexão');
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/api/web');
        stompClient = Stomp.over(socket);

        console.log(socket);

        //username é o header que será utilizado no interceptor para atribuir o socket id do usuario, se mudar o nome
        //tera que alterar o nome do header na classe CustomHandShakeInterceptor
        stompClient.connect({ username: username }, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
   if(document.querySelector('#chatByID').value.trim()!=""){
   idChat =document.querySelector('#chatByID').value.trim();
   console.log("Se inscrevendo para o ","/user/queue/messages")
    stompClient.subscribe( "/user/chats/queue/messages/"+idChat, onMessageReceived);//escutando para receber mensagems do chat
      stompClient.subscribe( "/user/chats/queue/messages", onMessageReceived);//escutando para receber que entrou no chat
       
      stompClient.send("/app/chats/chat.register",
              {},
              JSON.stringify({idUser: username, chatID:idChat, type: 'JOIN'})
          )

      console.log('Enviou o conect por id chat');
   }else{
   console.log("Fazendo registro no sistema, sem ID CHAT");
   let chatTitle=  document.querySelector('#chatTitle').value.trim();
   let maxParticipants = document.querySelector('#maxPart').value.trim();

    
     stompClient.send("/app/chats/chat.register",
            {},
            JSON.stringify({
                idUser: username,
                type: 'JOIN',
                chatTitle:chatTitle,
                maxParticipants:maxParticipants
            })
        )

     stompClient.subscribe('/user/chats/queue/messages', onMessageReceived);
     //stompClient.subscribe( "/user/chats/queue/messages/"+idChat, onMessageReceived);

    console.log('Enviou o conect para criar sala');
   }

    // Tell your username to the server

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Não foi possível se conectar ao WebSocket! Atualize a página e tente novamente ou entre em contato com o administrador.';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();
    
    if(messageContent && stompClient) {
        var chatMessage = {
            listOfParticipants: listOfParticipants,
            chatID:idChat,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chats/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

if(message.listOfParticipants!=null){
   listOfParticipants = message.listOfParticipants;
   console.log("lista de Participantes: ",listOfParticipants);
}

    if(message.chatID!=null && idChat==null){
    idChat = message.chatID;
    stompClient.subscribe("/user/chats/queue/messages/"+idChat,onMessageReceived);
  // stompClient.unsubscribe('/user/chats/queue/messages');
    alert(idChat);

    }
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {            
        messageElement.classList.add('event-message');
        message.content = message.nameUser + ' joined!';
      
        //register.unsubscribe(username);
  
        //stompClient.unsubscribe( "/user/chats/queue/messages/"+username,{});//retirando boas vindas caso entre em outro chat

        
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.idUserSender + ' left!';
    } else if (message.type === 'FULL') {
        alert("Sala Cheia");
        usernamePage.classList.remove('hidden');
        chatPage.classList.add('hidden');        
    } else if (message.type === 'ALREADY_IN' && message.listOfParticipants==null) {
        alert("Já incluso na sala, favor escolher outra");
        usernamePage.classList.remove('hidden');
        chatPage.classList.add('hidden');        
    }else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
       var avatarText = document.createTextNode(message.name);
       // var avatarText = document.createTextNode(message);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)