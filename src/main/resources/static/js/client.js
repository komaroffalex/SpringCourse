var orderApiGet = Vue.resource('/client/order?id={id}');
var orderApiGetAll = Vue.resource('/client/order/all?id={id}');
var foodApiGetAll = Vue.resource('/client/food/all');
var tableApiGetAll = Vue.resource('/client/table/all');
var reservationApiGet = Vue.resource('/client/reservation?id={id}');
var reservationApiGetAll = Vue.resource('/client/reservation/all?id={id}');
var currentUserGet = Vue.resource('/login/current');

var request = new XMLHttpRequest();
request.open('GET', '/login/current', false);  // `false` makes the request synchronous
request.send(null);
var myObj = JSON.parse(request.responseText);
var currUserId = myObj.id;

Vue.component('order-form', {
    props: ['orders'],
    data: function() {
        return {
            deltime: '',
            foodids: '',
            address: ''
        }
    },
    template:
        '<div>' +
        '<h5>Place new Order</h5>' +
        '<div>' +
        '<input type="text" placeholder="Write delivery time" v-model="deltime" />' +
        '<input type="text" placeholder="Write food ids (comma-separated)" v-model="foodids" />' +
        '<input type="text" placeholder="Write address" v-model="address" />' +
        '<input type="button" value="Save" v-on:click="save_order" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_order: function() {
            Vue.http.put('http://localhost:8080/client/order?deltime=' + this.deltime + '&foodids=' +
                this.foodids + '&address=' + this.address + '&clientid=' + currUserId).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.deltime = '';
                    this.foodids = '';
                    this.address = '';
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

Vue.component('food-row', {
    props: ['food', 'foods'],
    template:
        '<tr>' +
        '<td>{{ food.id }}</td><td>{{ food.name }}</td>' +
        '<td>{{ food.foodCost }}</td>' +
        '</tr>',
    methods: {

    }
});

Vue.component('table-row', {
    props: ['table', 'tables'],
    template:
        '<tr>' +
        '<td>{{ table.id }}</td><td>{{ table.seats }}</td>' +
        '<td>{{ table.location }}</td><td>{{ table.isOccupied }}</td>' +
        '</tr>',
    methods: {

    }
});

Vue.component('reservation-form', {
    props: ['reservations'],
    data: function() {
        return {
            restime: '',
            persons: '',
            tableid: ''
        }
    },
    template:
        '<div>' +
        '<h5>Add new Reservation</h5>' +
        '<div>' +
        '<input type="text" placeholder="Specify reservation time" v-model="restime" />' +
        '<input type="text" placeholder="Specify number of persons" v-model="persons" />' +
        '<input type="text" placeholder="Specify table id" v-model="tableid" />' +
        '<input type="button" value="Save" v-on:click="save_reservation" />' +
        '</div>' +
        '</div>'
    ,
    methods: {
        save_reservation: function() {
            Vue.http.put('http://localhost:8080/client/reservation?reservationtime=' + this.restime + '&persons=' +
                this.persons + '&tableid=' + this.tableid + '&clientid=' + currUserId).then(result =>
                result.json().then(data => {
                    console.log(data);
                    this.restime = '';
                    this.persons = '';
                    this.tableid = '';
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
    props: ['orders', 'foods', 'tables', 'reservations'],
    data: function() {
        return {
            curruser: ''
        }
    },
    template:
        '<div id="app">' +
        '<h4>Your id is:</h4>' + '<p id="puser">' + this.curruser + '</p>' +
        '<h4>Edit orders</h4>' +
        '<order-form :orders="orders" />' +
        '<h5>Registered orders</h5>' +
        '<table id="table2" style="width:70%">' +
        '<tr><th>Id</th><th>Address</th><th>Cost</th><th>DeliveryTime</th><th>Food</th><th>OrderStatus</th>' +
        '<th>WorkerId</th><th>ClientId</th><th>Remove</th></tr>'+
        '<order-row v-for="order in orders" :key="order.id" :order="order" :orders="orders" />' +
        '</table>' +
        '<h4>View food</h4>' +
        '<table id="table3" style="width:50%">' +
        '<tr><th>Id</th><th>FoodName</th><th>FoodCost</th></tr>' +
        '<food-row v-for="food in foods" :key="food.id" :food="food" :foods="foods" />' +
        '</table>' +
        '<h4>View tables</h4>' +
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

        Vue.http.get('http://localhost:8080/login/current').then(result =>
            result.json().then(data => {
                document.getElementById("puser").innerHTML = data.id;
            })
        );

        orderApiGetAll.get({id: currUserId}).then(result => {
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
        reservationApiGetAll.get({id: currUserId}).then(result => {
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
    template: '<messages-list :orders="orders" :foods="foods" :tables="tables" :reservations="reservations"/>',
    data: {
        orders: [],
        foods: [],
        tables: [],
        reservations: []
    }
});