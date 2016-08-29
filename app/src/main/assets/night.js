var body = document.body;

set_night_mode('night');

function set_night_mode(mode) {
    body.className += mode ? 'night ' : ' ';
    return 6;
}
