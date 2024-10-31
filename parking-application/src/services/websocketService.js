import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;

export const connectWebSocket = (onMessageReceived) => {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
            console.log('Connected to WebSocket server');
            stompClient.subscribe('/topic/user-logins', (message) => {
                onMessageReceived(JSON.parse(message.body));
            });
        },
        debug: (str) => {
            console.log(str);
        },
        reconnectDelay: 5000,
    });

    stompClient.activate();
};

export const disconnectWebSocket = () => {
    if (stompClient !== null) {
        stompClient.deactivate();
        console.log('Disconnected from WebSocket server');
    }
};
