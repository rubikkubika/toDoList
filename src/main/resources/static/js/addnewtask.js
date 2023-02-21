const content = document.getElementById('list-tab')

const tasktitle = document.getElementById('tasktitle')
const taskdescription = document.getElementById('taskdescription')
const taskstartdate = document.getElementById('taskstartdate')

var form = document.getElementById('addnewtaskform')
var validatenewtaskform = Boolean(true);

const createdatepicker = new tempusDominus.TempusDominus(document.getElementById('taskdatestart'), {
    display: {
        sideBySide: true,
    }
});
var today=new Date();


function addnewtask() {
    fetch(`/api/task/addnewtask`, {
        method: 'PUT', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            title: tasktitle.value,
            description: taskdescription.value,
            start: new Date((createdatepicker.dates.lastPicked - (createdatepicker.dates.lastPicked.getTimezoneOffset() * 60 * 1000)))

        }, (k, v) => v ?? undefined)
    })
        .then(() => {
            console.log(createdatepicker.dates.lastPicked)
            content.innerHTML = ''
            $('.filter li a').removeClass('clicked');
            $("#currentweekfilterbtn").addClass('clicked');
            getfilteredtask()
            $('.needs-validation').get(0).reset()
            $('#addnewtaskmodal').modal('hide');
            toastr.success("Задача создана");
        })
        .catch(error => console.log(error))
}

(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.addnewtaskform')

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
                    addnewtask()
                }
                validatenewtaskform = true
                form.classList.add('was-validated')
            }, false)
        })
})()
function getcurrentweek(d) {
    var sunday = new Date();
    d = new Date(d);
    var day = d.getDay(),
        diff = d.getDate() - day + (day == 0 ? -6:1); // adjust when day is sunday
    var monday =new Date(d.setDate(diff));
    sunday.setDate(monday.getDate()+6);
    return[monday.toISOString().slice(0,-1),sunday.toISOString().slice(0,-1)]
}

function getfilteredtask() {
    fetch(`api/task/filtered?startfilterdate=`+getcurrentweek(today).at(0)+`&endfilterdate=`+getcurrentweek(today).at(1), {
        method: 'GET'
    })
        .then(res => res.json())
        .then(data => {
                data.forEach(task => {

                    $("#list-tab").append('<a class="list-group-item list-group-item-action" id="' + task.id + '" data-bs-toggle="list"  role="tab">' + task.title + '</a>')
                })
            }
        )
}



