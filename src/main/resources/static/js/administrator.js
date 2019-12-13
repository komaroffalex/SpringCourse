var messageApiGet = Vue.resource('/admin/user?id={id}');
var messageApiGetAll = Vue.resource('/admin/user/all');
var orderApiGet = Vue.resource('/admin/order?id={id}');
var orderApiGetAll = Vue.resource('/admin/order/all');
var foodApiGet = Vue.resource('/admin/food?id={id}');
var foodApiGetAll = Vue.resource('/admin/food/all');
var tableApiGet = Vue.resource('/admin/table?id={id}');
var tableApiGetAll = Vue.resource('/admin/table/all');
var reservationApiGet = Vue.resource('/admin/reservation?id={id}');
var reservationApiGetAll = Vue.resource('/admin/reservation/all');

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
            '<h5>Add new user</h5>' +
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
            Vue.http.put('http://localhost:8080/admin/user?login=' + this.login + '&password=' +
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
    template:   '<tr>' +
                '<td>{{ message.id }}</td><td>{{ message.login }}</td>' +
                '<td>{{ message.password }}</td><td>{{ message.userName }}</td>' +
                '<td>{{ message.typeUser }}</td>' +
                '<td>' + '<input type="button" value="Remove user" v-on:click="del" />' + '</td>' +
                '</tr>',
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

Vue.component('order-form', {
    props: ['orders'],
    data: function() {
        return {
            deltime: '',
            foodids: '',
            address: '',
            clientid: ''
        }
    },
    template:
        '<div>' +
        '<h5>Add new Order</h5>' +
        '<div>' +
        '<input type="text" placeholder="Write delivery time" v-model="deltime" />' +
        '<input type="text" placeholder="Write food ids (comma-separated)" v-model="foodids" />' +
        '<input type="text" placeholder="Write address" v-model="address" />' +
        '<input type="text" placeholder="Specify client id" v-model="clientid" />' +
        '<input type="button" value="Save" v-on:click="save_order" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_order: function() {
            Vue.http.put('http://localhost:8080/admin/order?deltime=' + this.deltime + '&foodids=' +
                this.foodids + '&address=' + this.address + '&clientid=' + this.clientid).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.deltime = '';
                    this.foodids = '';
                    this.address = '';
                    this.clientid = '';
                })
            );
        }
    }
});

Vue.component('order-row', {
    props: ['order', 'orders'],
    template:
        '<tr>' +
        '<td>{{ order.id }}</td><td>{{ order.address }}</td>' +
        '<td>{{ order.cost }}</td><td>{{ order.deliveryTime }}</td>' +
        '<td>{{ order.food }}</td><td>{{ order.orderStatus }}</td>' +
        '<td>{{ order.worker.id }}</td><td>{{ order.client.id }}</td>' +
        '<td>' + '<input type="button" value="Remove order" v-on:click="del_order" />' + '</td>' +
        '</tr>',
    methods: {
        del_order: function(){
            orderApiGet.remove({id: this.order.id}).then(result => {
                if(result.ok) {
                    this.orders.splice(this.orders.indexOf(this.order), 1);
                    console.log(result.status)
                }
            })
        }
    }
});

Vue.component('food-form', {
    props: ['foods'],
    data: function() {
        return {
            foodname: '',
            foodcost: ''
        }
    },
    template:
        '<div>' +
        '<h5>Add new Food</h5>' +
        '<div>' +
        '<input type="text" placeholder="Write food name" v-model="foodname" />' +
        '<input type="text" placeholder="Write food cost" v-model="foodcost" />' +
        '<input type="button" value="Save" v-on:click="save_food" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_food: function() {
            Vue.http.put('http://localhost:8080/admin/food?foodname=' + this.foodname + '&foodcost=' +
                this.foodcost).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.foodname = '';
                    this.foodcost = '';
                })
            );
        }
    }
});

Vue.component('food-row', {
    props: ['food', 'foods'],
    template:
        '<tr>' +
        '<td>{{ food.id }}</td><td>{{ food.name }}</td>' +
        '<td>{{ food.foodCost }}</td>' +
        '<td>' + '<input type="button" value="Remove food" v-on:click="del_food" />' + '</td>' +
        '</tr>',
    methods: {
        del_food: function(){
            foodApiGet.remove({id: this.food.id}).then(result => {
                if(result.ok) {
                    this.foods.splice(this.foods.indexOf(this.food), 1);
                    console.log(result.status)
                }
            })
        }
    }
});

Vue.component('table-form', {
    props: ['tables'],
    data: function() {
        return {
            seats: '',
            location: '',
            isOccupied: ''
        }
    },
    template:
        '<div>' +
        '<h5>Add new Table</h5>' +
        '<div>' +
        '<input type="text" placeholder="Specify table seats" v-model="seats" />' +
        '<input type="text" placeholder="Specify table location" v-model="location" />' +
        '<input type="button" value="Save" v-on:click="save_table" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_table: function() {
            Vue.http.put('http://localhost:8080/admin/table?seats=' + this.seats + '&location=' +
                this.location).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.seats = '';
                    this.location = '';
                })
            );
        }
    }
});

Vue.component('table-row', {
    props: ['table', 'tables'],
    template:
        '<tr>' +
        '<td>{{ table.id }}</td><td>{{ table.seats }}</td>' +
        '<td>{{ table.location }}</td><td>{{ table.isOccupied }}</td>' +
        '<td>' + '<input type="button" value="Remove table" v-on:click="del_table" />' + '</td>' +
        '</tr>',
    methods: {
        del_table: function(){
            tableApiGet.remove({id: this.table.id}).then(result => {
                if(result.ok) {
                    this.tables.splice(this.tables.indexOf(this.table), 1);
                    console.log(result.status)
                }
            })
        }
    }
});

Vue.component('reservation-form', {
    props: ['reservations'],
    data: function() {
        return {
            restime: '',
            persons: '',
            tableid: '',
            clientid: ''
        }
    },
    template:
        '<div>' +
        '<h5>Add new Reservation</h5>' +
        '<div>' +
        '<input type="text" placeholder="Specify reservation time" v-model="restime" />' +
        '<input type="text" placeholder="Specify number of persons" v-model="persons" />' +
        '<input type="text" placeholder="Specify table id" v-model="tableid" />' +
        '<input type="text" placeholder="Specify client id" v-model="clientid" />' +
        '<input type="button" value="Save" v-on:click="save_reservation" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_reservation: function() {
            Vue.http.put('http://localhost:8080/admin/reservation?reservationtime=' + this.restime + '&persons=' +
                this.persons + '&tableid=' + this.tableid + '&clientid=' + this.clientid).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.restime = '';
                    this.persons = '';
                    this.tableid = '';
                    this.clientid = '';
                })
            );
        }
    }
});

Vue.component('reservation-row', {
    props: ['reservation', 'reservations'],
    template:
        '<tr>' +
        '<td>{{ reservation.id }}</td><td>{{ reservation.reservationTime }}</td>' +
        '<td>{{ reservation.persons }}</td><td>{{ reservation.table.id }}</td>' +
        '<td>{{ reservation.cost }}</td><td>{{ reservation.status }}</td>' +
        '<td>{{ reservation.client.id }}</td>' +
        '<td>' + '<input type="button" value="Remove reservation" v-on:click="del_reservation" />' + '</td>' +
        '</tr>',
    methods: {
        del_reservation: function(){
            reservationApiGet.remove({id: this.reservation.id}).then(result => {
                if(result.ok) {
                    this.reservations.splice(this.reservations.indexOf(this.reservation), 1);
                    console.log(result.status)
                }
            })
        }
    }
});

Vue.component('messages-list', {
    props: ['messages', 'orders', 'foods', 'tables', 'reservations'],
    template:
        '<div id="app">' +
        '<h4>Edit users</h4>' +
        '<message-form :messages="messages" />' +
        '<h5>Registered users</h5>' +
        '<table id="table" style="width:50%">' +
        '<tr><th>Id</th><th>Login</th><th>Password</th><th>UserName</th><th>Role</th><th>Remove</th></tr>' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" :messages="messages" />' +
        '</table>' +
        '<h4>Edit orders</h4>' +
        '<order-form :orders="orders" />' +
        '<h5>Registered orders</h5>' +
        '<table id="table2" style="width:50%">' +
        '<tr><th>Id</th><th>Address</th><th>Cost</th><th>DeliveryTime</th><th>Food</th><th>OrderStatus</th>' +
        '<th>WorkerId</th><th>ClientId</th><th>Remove</th></tr>'+
        '<order-row v-for="order in orders" :key="order.id" :order="order" :orders="orders" />' +
        '</table>' +
        '<h4>Edit food</h4>' +
        '<food-form :foods="foods" />' +
        '<h5>Registered food</h5>' +
        '<table id="table3" style="width:50%">' +
        '<tr><th>Id</th><th>FoodName</th><th>FoodCost</th></tr>' +
        '<food-row v-for="food in foods" :key="food.id" :food="food" :foods="foods" />' +
        '</table>' +
        '<h4>Edit tables</h4>' +
        '<table-form :tables="tables" />' +
        '<h5>Registered tables</h5>' +
        '<table id="table4" style="width:50%">' +
        '<tr><th>Id</th><th>Table seats</th><th>Location</th><th>isOccupied</th></tr>' +
        '<table-row v-for="table in tables" :key="table.id" :table="table" :tables="tables" />' +
        '</table>' +
        '<h4>Edit reservations</h4>' +
        '<reservation-form :reservations="reservations" />' +
        '<h5>Registered reservations</h5>' +
        '<table id="table5" style="width:50%">' +
        '<tr><th>Id</th><th>Reservation time</th><th>Persons</th><th>Table Id</th><th>Cost</th><th>Status</th>' +
        '<th>Client Id</th></tr>' +
        '<reservation-row v-for="reservation in reservations" :key="reservation.id" :reservation="reservation" :reservations="reservations" />' +
        '</table>' +
        '<span>' + '<input type="button" value="Log in Page" v-on:click="newpage" />' + '</span>' +
        '</div>',
    created: function() {
        messageApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(message => this.messages.push(message))
            )
        });
        orderApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(order => this.orders.push(order))
            )
        });
        foodApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(food => this.foods.push(food))
            )
        });
        tableApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(table => this.tables.push(table))
            )
        });
        reservationApiGetAll.get({}).then(result => {
            console.log(result);
            result.json().then( data =>
                data.forEach(reservation => this.reservations.push(reservation))
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
    template: '<messages-list :messages="messages" :orders="orders" :foods="foods" :tables="tables" :reservations="reservations"/>',
    data: {
        messages: [ ],
        orders: [],
        foods: [],
        tables: [],
        reservations: []
    }
});
