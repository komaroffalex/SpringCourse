var messageApiGet = Vue.resource('/user?id={id}');
var messageApiGetAll = Vue.resource('/user/all');

Vue.component('message-form', {
    props: ['messages'],
    data: function() {
        return {
            login: '',
            password: '',
            username: '',
            isAdmin: 'false',
            isClient: 'false',
            isWorker: 'false'
        }
    },
    template:
        '<div>' +
            '<div>' +
                '<input type="text" placeholder="Write login" v-model="login" />' +
                '<input type="text" placeholder="Write password" v-model="password" />' +
                '<input type="text" placeholder="Write username" v-model="username" />' +
                '<input type="button" value="Save" v-on:click="save" />' +
            '</div>' +
            '<div>' +
                '<input type="radio" id="userChoice1" name="admin" value="admin" v-model="isAdmin" v-on:click="checked1" />' +
                '<label for="userChoice1">' + 'Administrator' + '</label>' +
                '<input type="radio" id="userChoice2" name="client" value="client" v-model="isClient" v-on:click="checked2" />' +
                '<label for="userChoice2">' + 'Client' + '</label>' +
                '<input type="radio" id="userChoice3" name="worker" value="worker" v-model="isWorker" v-on:click="checked3" />' +
                '<label for="userChoice3">' + 'Worker' + '</label>' +
            '</div>' +
        '</div>'
    ,
    methods: {
        save: function() {
            var role = '-1';
            if (document.getElementById('userChoice1').checked == true) {
                role = '0';
            } else if (document.getElementById('userChoice2').checked == true) {
                role = '1';
            } else if (document.getElementById('userChoice3').checked == true) {
                role = '2';
            }
            Vue.http.put('http://localhost:8080/user?login=' + this.login + '&password=' +
                    this.password + '&username=' + this.username + '&role=' + role).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.login = '';
                    this.password = '';
                    this.username = '';
                })
            );
        },
        checked1: function() {
            document.getElementById('userChoice2').checked = false;
            document.getElementById('userChoice3').checked = false;
        },
        checked2: function() {
            document.getElementById('userChoice1').checked = false;
            document.getElementById('userChoice3').checked = false;
        },
        checked3: function() {
            document.getElementById('userChoice1').checked = false;
            document.getElementById('userChoice2').checked = false;
        }
    }

});

Vue.component('message-row', {
    props: ['message', 'messages'],
    template: '<div>{{ message.id }}' + ' ' + '{{ message.login }}' +
                '{{ message.password }}' + ' ' + '{{ message.userName }}' +
                '{{ message.typeUser }}' +
                '<span>' + '<input type="button" value="Remove user" v-on:click="del" />' + '</span>' + '</div>',
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
        '<span>' + '<input type="button" value="Index Page" v-on:click="newpage" />' + '</span>' +
        '</div>',
    created: function() {
        messageApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(message => this.messages.push(message))
            )
        });
    },
    methods: {
        newpage: function(){
            location.replace("index.html")
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
