window.onload = function () {

    document.getElementById("login").addEventListener("click", loginfunc, true);

   

}

function loginfunc(event) {
    event.preventDefault();

    let url = serverurl + 'user/login';
    let usr = document.getElementById("cod").value;
    let pass = document.getElementById("pass").value;
    let auth = {};
    let data = {nombre: usr, contraseña: pass};
    console.log(data);
    let init = {
        method: 'POST',
        headers: {
            mode: 'cors',
            'Accept': 'application/json',
            'Content-type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(data),
        cache: 'default'
    };

    fetch(url, init)
            .then((resp) => resp.json())
            .then(function (data) {
                if (data) {
                    console.log(data);
                    let token = data.token;
                    sessionStorage.setItem("USER_TOKEN", token);
                    console.log(token);
                    location.href = "admin/dashboard.html";

                    // let re = JSON.parse(atob(token.split('.')[1]));
                    //let url2 = serverurl + 'persona/codigo/' + re.sub;
                    //auth = re.authorities;

                    //let heads = new Headers();
                    //heads.append('Accept', 'application/json');
                    //heads.append('Content-type', 'application/json');
                    //heads.append('Access-Control-Allow-Origin', '*');
                    //heads.append('Authorization', token);
                    //console.log(token);
                    //let init2 = {
                    //    method: 'GET',
                    //    headers: heads,
                    //    cache: 'default'
                    //};
                    
                } else {

                    let msjdiv = document.getElementById("msjerr");
                    msjdiv.insertAdjacentHTML('afterbegin', '<div class="alert alert-warning alert-dismissible">' +
                            '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>' +
                            '<h5><i class="icon fas fa-exclamation-triangle"></i> Alert!</h5>' +
                            'contraseña incorreta</div>');
                }
            });

}


