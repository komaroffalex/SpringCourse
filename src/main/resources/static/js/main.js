Vue.component('message-form', {
    props: ['messages'],
    data: function() {
        return {
            login: '',
            password: ''
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Enter login" v-model="login" />' +
        '<input type="text" placeholder="Enter password" v-model="password" />' +
        '<input type="button" value="Log in" v-on:click="logIn" />' +
        '</div>',
    methods: {
        logIn: function() {
            Vue.http.get('http://localhost:8080/login?login=' + this.login + '&password=' +
                this.password).then(result => {
                    if(result.ok) {
                        result.json().then(data => {
                            console.log(data);
                            this.login = '';
                            this.password = '';
                            switch(data.toString()) {
                                case '0':
                                    location.replace("administrator.html");
                                    break;
                                case '1':
                                    location.replace("client.html");
                                    break;
                                case '2':
                                    location.replace("worker.html");
                                    break;
                                default:
                                    console.log('No such user!');
                            }
                        })
                    } else {
                        console.log(result);
                    }
                },
                result => {
                    console.log(result);
                }
            );
        }
    }

});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="app">' +
        '<message-form :messages="messages" />' +
        '</div>'
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data: {
        messages: [ ]
    }
});