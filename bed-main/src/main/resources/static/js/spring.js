window.addEventListener('DOMContentLoaded', function() {
    var springMain = document.querySelector('.springMain');
    springMain.classList.add('expand');
});

window.addEventListener('scroll', revealSection);

  function revealSection() {
    var sections = document.getElementsByClassName('container-fluid');
    for (var i = 0; i < sections.length; i++) {
      var section = sections[i];
      var sectionTop = section.getBoundingClientRect().top;
      var windowHeight = window.innerHeight;

      if (sectionTop < windowHeight - 300) {
        section.classList.add('active');
      }
    }
  }