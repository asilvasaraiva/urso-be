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
var register = null;
var privateMessage = null;

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

        stompClient.connect({ username: username }, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic

   if(document.querySelector('#chatByID').value.trim()!=""){
   idChat =document.querySelector('#chatByID').value.trim();
   console.log("Se inscrevendo para o ","/user/queue/messages")
      privateMessage = stompClient.subscribe( "/user/chats/queue/messages", onMessageReceived);
       stompClient.send("/app/chats/chat.register",
              {},
              JSON.stringify({sender: username, chatID:idChat, type: 'CHAT'})
          )

      console.log('Enviou o conect');
   }else{
   console.log("Fazendo registro no sistema, sem ID CHAT");
    register =  stompClient.subscribe('/user/chats/queue/messages', onMessageReceived);
     stompClient.send("/app/chats/chat.register",
            {},
            JSON.stringify({sender: username,type: 'JOIN',chatID:0})
        )

    console.log('Enviou o conect');
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
            sender: username,
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

    if(message.chatID!=null && idChat==null){
    idChat = message.chatID
    alert(idChat);
//    register.unsubscribe();
//    privateMessage = stompClient.subscribe( "/user/queue/messages", onMessageReceived);
    }
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.name + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.idUserSender + ' left!';
    } else {
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