/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let authToken = sessionStorage.getItem("USER_TOKEN");
let url = serverurl;
let grupo = {};

if (authToken === null) {
    console.log('no hay sesion');
    location.href = "../login.html";
} else {


    console.log(authToken);
    console.log(url);
    const vm = new Vue({
        el: 'main',
        data: {
            materiales: {
                lista: [{
                        id: 0,
                        nombre: 'a',
                        descripcion: 'a',
                    }]
            },
            mateedit: '',
            mateauxiliar: {},
        },
        methods: {
            cargamateriales: cargamateriales,
            registromaterial: registromaterial,
            auxmaterial: auxmaterial,
            editar: editar,
            eliminar: eliminar,

        },
        created: cargamateriales
    });
    window.onload = function () {

        document.getElementById("cerrars").addEventListener("click", function () {
            sessionStorage.removeItem("USER_TOKEN");
            location.href = "../login.html";
        });
    }

    function eliminar(evemt) {
        let val = event.target.value;
        let data = {
            id: val,
            nombre: '',
            descripcion: '',
        };
        console.log(data);
        let init = makeinit(data, authToken);
        let admurlc = url + 'material/borrar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    cargamateriales();
                });
    }

    function editar() {
        let val = vm.mateedit;
        let nom = '';
        let desc = '';

        if (document.getElementById("editnommat") !== null) {
            nom = document.getElementById("editnommat").value;
        }

        if (document.getElementById("editdescmat") !== null) {
            desc = document.getElementById("editdescmat").value;
        }


        let data = {
            id: val,
            nombre: nom,
            descripcion: desc,
        };

        console.log(data);


        let init = makeinit(data, authToken);
        let admurlc = url + 'material/modificar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    cargamateriales();
                });
    }

    function auxmaterial(event) {
        let val = event.target.value;
        vm.mateedit = val;
        let data = {
            id: val,
            nombre: '',
            descripcion: '',
        };


        let init = makeinit(data, authToken);
        let admurlc = url + 'material/consulta';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    vm.mateauxiliar = data;
                    console.log(vm.mateauxiliar)
                    document.getElementById("editnommat").value = data.nombre;
                    document.getElementById("editdescmat").value = data.descripcion;
                });
    }

    function registromaterial() {

        let nom = '';
        let desc = '';

        if (document.getElementById("nommat") !== null) {
            nom = document.getElementById("nommat").value;
        }

        if (document.getElementById("descmat") !== null) {
            desc = document.getElementById("descmat").value;
        }

        let data = {
            id: 0,
            nombre: nom,
            descripcion: desc,
        };

        console.log(data);


        let init = makeinit(data, authToken);
        let admurlc = url + 'material/nuevo';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    cargamateriales();
                });
    }

    function cargamateriales() {


        let init = makeinitnodat(authToken);

        let admurlc = url + 'material/listar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {

                    console.log(data);
                    vm.materiales = data;
                    console.log(vm.materiales);


                });
    }

    function makeinit(data, authToken) {
        let heads = new Headers();
        heads.append("Accept", "application/json");
        heads.append("Content-Type", "application/json");
        heads.append("Authorization", authToken);
        heads.append("Access-Control-Allow-Origin", '*');
        let init = {
            method: 'POST',
            mode: 'cors',
            body: JSON.stringify(data),
            headers: heads
        };
        return init;
    }

    function makeinitnodat(authToken) {
        let heads = new Headers();
        heads.append("Accept", "application/json");
        heads.append("Content-Type", "application/json");
        heads.append("Authorization", authToken);
        heads.append("Access-Control-Allow-Origin", '*');
        let init = {
            method: 'POST',
            mode: 'cors',
            headers: heads
        };
        return init;
    }

}
