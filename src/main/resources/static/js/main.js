var messageApiGet = Vue.resource('/user?id={id}');
var messageApiGetAll = Vue.resource('/user/all');
var newPage = Vue.resource('/user/page');
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
                    this.login = '';
                    this.password = '';
                    this.username = '';
                })
            );
        }
    }

});

Vue.component('message-row', {
    props: ['message', 'messages'],
    template: '<div><i>({{message.id}})</i> <i>({{message.login}})</i> ' +
                '<i>({{message.password}})</i> <i>({{message.userName}})</i>' +
                '<span>' + '<input type="button" value="X" v-on:click="del" />' + '</span>' + '</div>',
    methods: {
        del: function(){
            messageApiGet.remove({id: this.message.id}).then(result => {
                if(result.ok) {
                    this.messages.splice(this.messages.indexOf(this.message), 1);
                    console.log(result.status)
                }
            })
        }
    }
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="app">' +
        '<message-form :messages="messages" />' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" :messages="messages" />' +
        '<span>' + '<input type="button" value="Admin Page" v-on:click="newpage" />' + '</span>' +
        '</div>',
    created: function() {
        messageApiGetAll.get({}).then(result => {
            result.json().then( data =>
                data.forEach(message => this.messages.push(message))
            )
        });
    },
    methods: {
        newpage: function(){
            location.replace("administrator.html")
        }
    }

});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data: {
        messages: [ ]
    }
});
