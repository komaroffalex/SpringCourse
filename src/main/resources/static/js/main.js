var messageApi = Vue.resource('/user?id={id}');

Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{message.id}})</i> {{message.login}} </div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="app">' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" />' +
        '</div>',
    created: function() {
        messageApi.get({id: 1}).then(result => {
            result.json().then( data =>
                this.messages.push(data)
            )
        });
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data: {
        messages: [ ]
    }
});
