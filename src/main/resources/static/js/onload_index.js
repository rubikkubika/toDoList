var form = document.getElementById('editform')
let openedtask = ''
var today = new Date();
const content = document.getElementById('list-tab')
const contentdetails = document.getElementById('contentdetails')
const completedtasklist = document.getElementById('completedtasklist')


var validatenewtaskform = Boolean(true);

const editdatepicker = new tempusDominus.TempusDominus(document.getElementById('edittaskdatestart'), {
    display: {
        sideBySide: true,
    }
});

function getcurrentweek(d) {
    var sunday = new Date();
    d = new Date(d);
    var day = d.getDay(),
        diff = d.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday
    var monday = new Date(d.setDate(diff));
    sunday.setDate(monday.getDate() + 6);
    return [monday.toISOString().slice(0, -1), sunday.toISOString().slice(0, -1)]
}


window.addEventListener("load", () => {
    getfilteredtask()
})

function getfilteredtask() {
    fetch(`api/task/filtered?startfilterdate=` + getcurrentweek(today).at(0) + `&endfilterdate=` + getcurrentweek(today).at(1), {
        method: 'GET'
    })
        .then(res => res.json())
        .then(data => {
            data.forEach(task => {

                $("#list-tab").append('<a class="list-group-item list-group-item-action" id="' + task.id + '" data-bs-toggle="list"  role="tab">' + task.title + '</a>')
            })
        })
        .then(() => {
            fetch(`api/task/toptencompleted`, {
                method: 'GET'
            }).then(res => res.json())
                .then(data => {
                    data.forEach(task => {
                        $("#completedtasklist").append('<a class="list-group-item list-group-item-action" id="' + task.id + '" data-bs-toggle="list"  role="tab">' + task.title + '</a>')
                    })
                })
        })
}

$(document).on("click", ".list-group-item-action", function () {
    $('.list-group-item-action').removeClass('active');
    $(this).addClass('active');
})

$('.filter li a').click(function () {
    $('.filter li a').removeClass('clicked');
    $(this).addClass('clicked');
})
$(document).on("click", ".list-group-item", function () {
    fetch(`api/task/` + this.id, {
        method: 'GET'
    }).then(res => res.json())
        .then(data => {
            openedtask = data
            if (data.status === "CREATED") {
                document.querySelector("#contentdetails").innerHTML = `<div class="card">
  <div class="card-header">` + data.title +

                    `</div>
  <div class="card-body">
       <p class="card-text">` + data.description + `</p>
       <p class="card-text">Дедлайн  ` + new Date(data.start) + `</p>
       <a href="#" class="btn btn-success" id="executetaskbtn">Выполнить</a>
    <a href="#" class="btn btn-primary" id="edittaskbtn">Редактировать</a>
    <a href="#" class="btn btn-danger" id="deletetaskbtn">Удалить</a>
  </div>
</div>`
            } else {
                document.querySelector("#contentdetails").innerHTML = `<div class="card">
  <div class="card-header">` + data.title +

                    `</div>
  <div class="card-body">
       <p class="card-text">` + data.description + `</p>
       <p class="card-text">Дедлайн  ` + new Date(data.start) + `</p>

    <a href="#" class="btn btn-danger" id="deletetaskbtn">Удалить</a>
  </div>
</div>`

            }

        })
});

$(document).on("click", "#addnewtaskbtn", function () {
    form.classList.remove('was-validated')
    $('#addnewtaskmodal').modal('show');
});

$(document).on("click", "#deletetaskbtn", function () {
    fetch(`api/task/deletetask?id=` + openedtask.id, {
        method: 'DELETE'
    }).then(() => {
        content.innerHTML = ''
        contentdetails.innerHTML = ''
        completedtasklist.innerHTML = ''
        openedtask = ``
        $('.filter li a').removeClass('clicked');
        $("#currentweekfilterbtn").addClass('clicked');
        getfilteredtask()
    })

});

$(document).on("click", "#executetaskbtn", function () {
    fetch(`api/task/executetask?id=` + openedtask.id, {
        method: 'PATCH', headers: {'Content-Type': 'application/json'}

    }).then(() => {
        console.log(JSON.stringify(openedtask))
        content.innerHTML = ''
        contentdetails.innerHTML = ''
        completedtasklist.innerHTML = ''
        openedtask = ``
        $('.filter li a').removeClass('clicked');
        $("#currentweekfilterbtn").addClass('clicked');
        getfilteredtask()
        toastr.success("Задача выполнена");
    })

});

$(document).on("click", "#nextweekfilterbtn", function () {
    today.setDate(today.getDate() + 7)
    content.innerHTML = ''
    contentdetails.innerHTML = ``
    completedtasklist.innerHTML = ``
    getfilteredtask();
    today.setDate(today.getDate() - 7)

})

$(document).on("click", "#currentweekfilterbtn", function () {

    content.innerHTML = ''
    contentdetails.innerHTML = ``
    completedtasklist.innerHTML = ``
    getfilteredtask();

})

$(document).on("click", "#edittaskbtn", function () {
        form.classList.remove('was-validated')
        $('#edittaskmodal').find('.modal-title').text("Редактирование задачи")
        editdatepicker.dates.setValue(editdatepicker.dates.parseInput(new Date(openedtask.start)), editdatepicker.dates.lastPickedIndex)
        $('#edittaskmodal').find('#edittasktitle').val(openedtask.title)
        $('#edittaskmodal').find('#edittaskdescription').val(openedtask.description)
        $('#edittaskmodal').modal('show');
    }
)

function edittask() {
    fetch(`/api/task/edittask`, {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: openedtask.id,
            title: openedtask.title,
            description: openedtask.description,
            start: new Date((editdatepicker.dates.lastPicked - (editdatepicker.dates.lastPicked.getTimezoneOffset() * 60 * 1000))),
            status: "CREATED"
        }, (k, v) => v ?? undefined)
    })
        .then(() => {

            content.innerHTML = ''
            completedtasklist.innerHTML=''
            $('.filter li a').removeClass('clicked');
            $("#currentweekfilterbtn").addClass('clicked');
            getfilteredtask()
            $('.editform').get(0).reset()
            $('#edittaskmodal').modal('hide');
            toastr.success("Задача отредактирована");
        })
        .catch(error => console.log(error))
}


(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.editform')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                    validatenewtaskform = false
                }
                if (validatenewtaskform === true) {
                    edittask()
                }
                validatenewtaskform = true
                form.classList.add('was-validated')
            }, false)
        })
})()



