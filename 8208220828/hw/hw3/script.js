document.addEventListener('DOMContentLoaded', function() {
    var submenus = document.querySelectorAll('.submenu');
    var arrows = document.querySelectorAll('.arrow');

    arrows.forEach(function(arrow, index) {
        arrow.addEventListener('click', function() {
            var submenu = submenus[index];
            submenu.style.display = (submenu.style.display === 'block') ? 'none' : 'block';
        });
    });
});