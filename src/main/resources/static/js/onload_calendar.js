var calendar;
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
     calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'ru',
        timeZone: 'local',
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        height: 800,
        eventDisplay:'list-item',
        dayMaxEvents: true,
        eventMaxStack:2,
        eventSources: [

            // your event source
            {
                url: "/api/task/all", // use the `url` property// an option!
                textColor: 'black'  // an option!
            }

            // any other sources...

        ]
    });
    calendar.render();
});



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
$(document).on("click", "#addnewtaskbtn", function() {
    form.classList.remove('was-validated')
    $('#addnewtaskmodal').modal('show');
});
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

            $('.needs-validation').get(0).reset()
            $('#addnewtaskmodal').modal('hide');
            calendar.refetchEvents();
            toastr.success("Задача создана");
        })
        .catch(error => console.log(error))
}
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

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
