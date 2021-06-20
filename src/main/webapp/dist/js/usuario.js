let url = serverurl;

let map = null;
let punto = null;

let geolocation = null;
let  bingKey = 'Ah41iutd3LvB8dIZkp0CLBSe3UDYMoIwraSMcTK4RJJIlwGPbVOIDrLhJOJrYA31';

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
        puntoaux: 3,
        puntomaterialaux: 0,
        puntoedit: {id: 0,
            nombre: 'a',
            direccion: 'a',
            latitud: 0,
            longitud: 0,
        },
        mensajeeditar: '',
        mapa: {},

    },
    methods: {
        cargapuntos: cargapuntos,
        mostrarMapa: mostrarMapa,
        mostrarRuta: mostrarRuta,
        regresar: regresar,

    },
    created: inicio
});

function regresar() {
    var pos = geolocation.getPosition();
    map.getView().setCenter(pos);

}

function mostrarMapa(event) {
    let val = event.target.value;
    let vals = val.split('/');
    let id = vals[0];
    let latitud = parseFloat(vals[1]);
    let longitud = parseFloat(vals[2]);


    if (punto !== null) {
        punto.setVisible(false);
    }


    let place = ol.proj.fromLonLat([longitud, latitud]);

    punto = createPoint(place)
    map.addLayer(punto);
    map.getView().setCenter(place);

}

function mostrarRuta(event) {


    let val = event.target.value;
    let vals = val.split('/');

    let latitud = parseFloat(vals[1]);
    let longitud = parseFloat(vals[2]);

    if (punto !== null) {
        punto.setVisible(false);
    }

    let place = [latitud, longitud];

    let pos = geolocation.getPosition();

    let placestart = ol.proj.transform(pos, 'EPSG:3857', 'EPSG:4326').reverse();

    obtenerRuta(placestart, place, bingKey, map);

}

function inicio() {
    mapa();
    cargapuntos();

}

function mapa() {

    let layers = createBingLayer(bingKey);
    var place = ol.proj.fromLonLat([-72.5281851592272, 7.9027676125098765]);
    let view = createView(place);
    map = createMap(layers, view);
    geolocation = createGeolocation(view);

    var iconStyle = createIconStyle();

    var iconFeature = new ol.Feature();

    var iconSource = createIconSource(iconFeature);

    var iconLayer = createIconLayer(iconSource, iconStyle);

    map.addLayer(iconLayer);

    geoloc(geolocation, iconFeature, map);

}

function geoloc(geolocation, iconFeature, map) {

    let cont = 0;

    geolocation.on('change:position', function () {
        var pos = geolocation.getPosition();
        iconFeature.setGeometry(new ol.geom.Point(pos));
        if (!cont) {
            map.getView().setCenter(pos);
        }
    });
}


function getMateriales(idpunto, index) {
    let data = {
        id: idpunto,
        nombre: '',
        direccion: '',
        latitud: 0,
        longitud: 0
    };

    let init = makeinit(data);

    let admurlc = url + 'material/porpunto';

    fetch(admurlc, init)
            .then((resp) => resp.json())
            .then(function (data) {
                vm.puntos[index].materiales = data;
            });
}

function cargapuntos() {
    let init = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json',
            'Access-Control-Allow-Origin': '*',

        },
        cache: 'default'
    };
    let admurlc = url + 'punto/listar';
    fetch(admurlc, init)
            .then((resp) => resp.json())
            .then(function (data) {

                vm.puntos = data;


                for (let i = 0; i < vm.puntos.length; i++) {

                    getMateriales(vm.puntos[i].id, i);
                }
            });
}

function makeinit(data) {
    let heads = new Headers();
    heads.append("Accept", "application/json");
    heads.append("Content-Type", "application/json");

    heads.append("Access-Control-Allow-Origin", '*');
    let init = {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify(data),
        headers: heads
    };
    return init;
}