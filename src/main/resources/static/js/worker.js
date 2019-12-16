var orderApiGet = Vue.resource('/worker/order?id={id}');
var orderApiGetAll = Vue.resource('/worker/order/all?id={id}');
var foodApiGetAll = Vue.resource('/worker/food/all');
var currentUserGet = Vue.resource('/login/current');

var request = new XMLHttpRequest();
request.open('GET', '/login/current', false);  // `false` makes the request synchronous
request.send(null);
var myObj = JSON.parse(request.responseText);
var currUserId = myObj.id;

Vue.component('order-row', {
    props: ['order', 'orders'],
    template:
        '<tr>' +
        '<td>{{ order.id }}</td><td>{{ order.address }}</td>' +
        '<td>{{ order.cost }}</td><td>{{ order.deliveryTime }}</td>' +
        '<td>{{ order.food }}</td><td>{{ order.orderStatus }}</td>' +
        '<td>{{ order.worker.id }}</td><td>{{ order.client.id }}</td>' +
        '<td>' + '<input type="button" value="Remove order" v-on:click="del_order" />' + '</td>' +
        '<td>' + '<input type="button" value="Finish order" v-on:click="fin_order" />' + '</td>' +
        '<td>' + '<input type="button" value="Deny order" v-on:click="deny_order" />' + '</td>' +
        '</tr>',
    methods: {
        del_order: function(){
            orderApiGet.remove({id: this.order.id}).then(result => {
                if(result.ok) {
                    this.orders.splice(this.orders.indexOf(this.order), 1);
                    console.log(result.status)
                }
            })
        },
        fin_order: function() {
            Vue.http.put('http://localhost:8080/worker/order/status?orderid=' + this.order.id + '&newstatus=' +
                '2').then(result =>
                result.json().then(data => {
                    console.log(data);
                })
            );
        },
        deny_order: function() {
            Vue.http.put('http://localhost:8080/worker/order/status?orderid=' + this.order.id + '&newstatus=' +
                '3').then(result =>
                result.json().then(data => {
                    console.log(data);
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
        '</tr>',
    methods: {

    }
});

Vue.component('messages-list', {
    props: ['orders', 'foods'],
    data: function() {
        return {
            curruser: ''
        }
    },
    template:
        '<div id="app">' +
        '<h4>Your id is:</h4>' + '<p id="puser">' + this.curruser + '</p>' +
        '<h4>Edit orders</h4>' +
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
    },
    methods: {
        newpage: function(){
            location.replace("index.html")
        }
    }

});

var app = new Vue({
    el: '#app',
    template: '<messages-list :orders="orders" :foods="foods"/>',
    data: {
        orders: [],
        foods: []
    }
});