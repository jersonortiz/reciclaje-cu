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
    let mapaa = null;
    console.log(authToken);
    console.log(url);
    const vm = new Vue({
        el: 'main',
        data: {
            puntos: {
                lista: [{
                        id: 0,
                        nombre: 'a',
                        direccion: 'a',
                        latitud: 0,
                        longitud: 0,
                    }],
            },
            materiales: {
                lista: [{
                        id: 0,
                        nombre: 'a',
                        descripcion: 'a',
                    }]
            },
            materialesañadir: {
                lista: [{
                        id: 0,
                        nombre: 'a',
                        descripcion: 'a',
                    }]
            },
            puntoaux: 3,
            puntomaterialaux: 0,
            puntoedit: {id: 0,
                nombre: 'a',
                direccion: 'a',
                latitud: 0,
                longitud: 0,
            },
            mensajeeditar: '',

        },
        methods: {
            cargapuntos: cargapuntos,
            registropunto: registropunto,
            puntoauxfunc: puntoauxfunc,
            editpunto: editpunto,
            cargamateriales: cargamateriales,
            mapa: mapa,
            quitarpunto: quitarpunto,
            cargarmaterialesañadir: cargarmaterialesañadir,
            asignarmateriales: asignarmateriales,
            borrar: borrar

        },
        created: cargapuntos
    });

    window.onload = function () {


        document.getElementById("cerrars").addEventListener("click", function () {
            sessionStorage.removeItem("USER_TOKEN");
            location.href = "../login.html";
        });
    }


    function borrar(event) {
        let idp = event.target.value;
        let data = {
            id: idp,
            nombre: '',
            direccion: '',
            latitud: 0,
            longitud: 0
        };




        let init = makeinit(data, authToken);
        let admurlc = url + 'punto/borrar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {

                    cargapuntos();


                });


    }



    function asignarmateriales() {
        let mats = document.getElementsByName("materialesañadir");
        console.log(mats.length);
        seleccionados = []
        for (let i = 0; i < mats.length; i++) {
            if (mats[i].checked) {
                seleccionados.push(mats[i].value)
            }
        }

        let data = {
            id: vm.puntomaterialaux,
            nombre: '',
            direccion: '',
            latitud: 0,
            longitud: 0,
            materiales: seleccionados,
        };

        console.log(data);


        let init = makeinit(data, authToken);
        let admurlc = url + 'material/asignar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    vm.materialesañadir = data;
                    console.log(vm.materialesañadir);
                    getMateriales(vm.puntomaterialaux);
                });


    }

    function cargarmaterialesañadir() {
        console.log('in')
        idpunto = vm.puntomaterialaux;

        let data = {
            id: idpunto,
            nombre: '',
            direccion: '',
            latitud: 0,
            longitud: 0
        };

        console.log(data);

        let init = makeinit(data, authToken);
        let admurlc = url + 'material/listarporanadir';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    vm.materialesañadir = data;
                    console.log(vm.materialesañadir);
                });


    }

    function quitarpunto(event) {

        idpunto = vm.puntomaterialaux;
        idmaterial = event.target.value;


        let data = {
            id: 0,
            idpunto: idpunto,
            idmaterial: idmaterial
        };

        let init = makeinit(data, authToken);

        let admurlc = url + 'material/desasignar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {

                    console.log(data);

                    getMateriales(idpunto);

                });

    }

    function mapa(event) {
        let val = event.target.value;
        let vals = val.split('/');
        let id = vals[0];
        let latitud = parseFloat(vals[1]);
        let longitud = parseFloat(vals[2]);

        if (mapaa !== null) {
            mapaa.setTarget(null);
        }

        let  bingKey = 'Ah41iutd3LvB8dIZkp0CLBSe3UDYMoIwraSMcTK4RJJIlwGPbVOIDrLhJOJrYA31';
        let layers = createBingLayer(bingKey);
        var place = ol.proj.fromLonLat([longitud, latitud]);
        console.log(place);

        let view = createView(place);

        console.log('algo');

        mapaa = createMap(layers, view);
        console.log('algo');
        let punto = createPoint(place)
        mapaa.addLayer(punto);

    }

    function cargamateriales(event) {
        console.log('algo');
        let val = event.target.value;
        console.log(val);
        vm.puntomaterialaux = val;
        console.log(vm.puntomaterialaux);
        getMateriales(val);

    }

    function getMateriales(idpunto) {
        let data = {
            id: idpunto,
            nombre: '',
            direccion: '',
            latitud: 0,
            longitud: 0
        };

        let init = makeinit(data, authToken);

        let admurlc = url + 'material/porpunto';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {

                    console.log(data);
                    vm.materiales = data;
                    console.log(vm.materiales);
                    cargarmaterialesañadir();

                });
    }




    function cargapuntos() {
        let init = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': authToken
            },
            cache: 'default'
        };
        let admurlc = url + 'punto/listar';
        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    vm.puntos = data;
                    console.log(vm.puntos);
                });
    }

    function registropunto() {

        let nom = null;
        let dir = null;
        let lat = null;
        let lon = null;

        if (document.getElementById("puntonom") !== null) {
            nom = document.getElementById("puntonom").value;
        }

        if (document.getElementById("puntodir") !== null) {
            dir = document.getElementById("puntodir").value;
        }

        if (document.getElementById("puntolat") !== null) {
            lat = document.getElementById("puntolat").value;
        }

        if (document.getElementById("puntolon") !== null) {
            lon = document.getElementById("puntolon").value;
        }

        let data = {
            id: 0,
            nombre: nom,
            direccion: dir,
            latitud: lat,
            longitud: lon
        };

        let init = makeinit(data, authToken);
        let admurlc = url + 'punto/nuevo';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    vm.materiales = data;
                    console.log(vm.materiales);
                    cargapuntos();

                    document.getElementById("puntonom").value = '';
                    document.getElementById("puntodir").value = '';
                    document.getElementById("puntolat").value = '';
                    document.getElementById("puntolon").value = '';
                });

    }

    function puntoauxfunc(event) {
        let val = event.target.value;
        vm.puntoauxfunc = val;
        vm.mensajeeditar = '';


        let data = {
            id: val,
            nombre: '',
            direccion: '',
            latitud: 0,
            longitud: 0
        };

        console.log(data);

        let init = makeinit(data, authToken);
        let admurlc = url + 'punto/especifico';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    vm.puntoedit = data;
                    console.log(vm.puntoedit);


                    document.getElementById("editpuntonom").value = data.nombre;
                    document.getElementById("editpuntodir").value = data.direccion;
                    document.getElementById("editpuntolat").value = data.latitud;
                    document.getElementById("editpuntolon").value = data.longitud;
                });


    }

    function editpunto() {

        let idp = vm.puntoauxfunc;
        let nom = null;
        let dir = null;
        let lat = null;
        let lon = null;

        if (document.getElementById("editpuntonom") !== null) {
            nom = document.getElementById("editpuntonom").value;
        }

        if (document.getElementById("editpuntodir") !== null) {
            dir = document.getElementById("editpuntodir").value;
        }

        if (document.getElementById("editpuntolat") !== null) {
            lat = document.getElementById("editpuntolat").value;
        }

        if (document.getElementById("editpuntolon") !== null) {
            lon = document.getElementById("editpuntolon").value;
        }

        let data = {
            id: idp,
            nombre: nom,
            direccion: dir,
            latitud: lat,
            longitud: lon
        };

        console.log(data);



        let init = makeinit(data, authToken);
        let admurlc = url + 'punto/editar';

        fetch(admurlc, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    console.log(data);
                    cargapuntos();
                    if (data.msg === 'ok') {
                        vm.mensajeeditar = 'actualizado correctamente';

                    }

                    document.getElementById("editpuntonom").value = '';
                    document.getElementById("editpuntodir").value = '';
                    document.getElementById("editpuntolat").value = '';
                    document.getElementById("editpuntolon").value = '';
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

}