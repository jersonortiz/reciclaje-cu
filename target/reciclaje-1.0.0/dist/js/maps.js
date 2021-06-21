function createIconStyle() {
    return new ol.style.Style({
        image: new ol.style.Circle({
            radius: 6,
            fill: new ol.style.Fill({
                color: '#3399CC'
            }),
            stroke: new ol.style.Stroke({
                color: '#fff',
                width: 2
            })
        })
    });
}

function createIconLayer(iconSource, iconStyle) {
    return new ol.layer.Vector({
        source: iconSource,
        style: iconStyle
    });
}

function createIconSource(iconFeature) {
    return new ol.source.Vector({
        features: [iconFeature]
    });
}

function createGeolocation(view) {
    let geolocation = new ol.Geolocation({
        // enableHighAccuracy must be set to true to have the heading value.
        trackingOptions: {
            enableHighAccuracy: true,
        },
        projection: view.getProjection(),
        tracking: true,
        trackingOptions: {
            enableHighAccuracy: true,
            maximumAge: 2000
        }
    });

    return geolocation;
}

function obtenerRuta(inicio, fin, bingKey, map) {
    let url = "https://dev.virtualearth.net/REST/v1/Routes?wp.0=" + inicio + "&wp.1=" + fin +
            "&routeAttributes=routePath&key=" + bingKey;

    let init = {
        method: 'GET',
    };

    fetch(url, init)
            .then((resp) => resp.json())
            .then(function (data) {

                let linestring = data.resourceSets[0].resources[0].routePath.line.coordinates;
                let layerLines = createLines(linestring);
                map.addLayer(layerLines);

                var pointlayer = ol.proj.fromLonLat(linestring[linestring.length - 1]);


                map.addLayer(createPoint(pointlayer));


                var pointlayerini = ol.proj.fromLonLat(linestring[0]);
                map.getView().setCenter(pointlayerini);
                map.addLayer(createPoint(pointlayerini));

            }).catch(function (err) {
        console.log(`Error: ${err}`)
    });
}

function createLines(linestring) {
    for (var i = 0; i < linestring.length; i++) {
        linestring[i].reverse();
    }

    return new ol.layer.Vector({
        source: new ol.source.Vector({
            features: [new ol.Feature({
                    geometry: new ol.geom.LineString(linestring).transform('EPSG:4326', 'EPSG:3857'),
                    name: 'Line'
                })]
        }),
    });
}

function createBingLayer(bingKey) {
    let layers = [];

    let point = new ol.layer.Tile({
        visible: true,
        preload: Infinity,
        source: new ol.source.BingMaps({
            //key: 'Your Bing Maps Key from http://www.bingmapsportal.com/ here',
            key: bingKey,

            imagerySet: 'AerialWithLabelsOnDemand',
            // use maxZoom 19 to see stretched tiles instead of the BingMaps
            // "no photos at this zoom level" tiles
            // maxZoom: 19
        }),
    });

    layers.push(point);

    return layers;
}

function createPoint(coor) {
    var point = new ol.geom.Point(coor);

    let punto = new ol.layer.Vector({
        source: new ol.source.Vector({
            features: [new ol.Feature(point)],
        }),
        style: new ol.style.Style({
            image: new ol.style.Circle({
                radius: 6,
                fill: new ol.style.Fill({color: 'red'}),
            }),
        }),
    });

    return punto;
}

function createMap(layers, view) {

    return new ol.Map({
        layers: layers,
        target: 'map',
        view: view,
    });

}

function createView(place) {
    return new ol.View({
        center: place,
        zoom: 18,
        maxZoom: 20,
        minZoom: 13,
    })
}