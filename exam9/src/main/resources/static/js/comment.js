'use strict';

async function addComment(form){
    let data = new FormData(form);
    fetch("http://localhost:8080/comment/add", {
        method: 'POST',
        body: data
    }).then(r => r.json()).then(data => {
        window.location.href = "http://localhost:8080"
    });
    location.reload();
}
