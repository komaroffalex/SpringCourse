Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{message.id}})</i> {{message.text}} </div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="app">' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" />' +
        '</div>'
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data: {
        messages: [
            {id: '123', text: 'Wow'},
            {id: '23', text: 'Wow2'},
            {id: '3', text: 'Wow3'},
        ]
    }
});
