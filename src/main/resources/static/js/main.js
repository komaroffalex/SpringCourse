var messageApiGet = Vue.resource('/user?id={id}');
var messageApiGetAll = Vue.resource('/user/all');
var messageApiPut = Vue.resource('/user?login={login}&password={password}&username={username}');

Vue.component('message-form', {
    props: ['messages'],
    data: function() {
        return {
            login: '',
            password: '',
            username: ''
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write login" v-model="login" />' +
            '<input type="text" placeholder="Write password" v-model="password" />' +
            '<input type="text" placeholder="Write username" v-model="username" />' +
            '<input type="button" value="Save" v-on:click="save" />' +
        '</div>',
    methods: {
        save: function() {

            Vue.http.put('http://localhost:8080/user?login=' + this.login + '&password=' +
                    this.password + '&username=' + this.username).then(result =>
                result.json().then(data => {
                    console.log(data);
                })
            );
        }
    }

});

Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{message.id}})</i> <i>({{message.login}})</i> ' +
                '<i>({{message.password}})</i> <i>({{message.userName}})</i></div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="app">' +
        '<message-form :messages="messages" />' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" />' +
        '</div>',
    created: function() {
        messageApiGetAll.get({}).then(result => {
            result.json().then( data =>
                data.forEach(message => this.messages.push(message))
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
