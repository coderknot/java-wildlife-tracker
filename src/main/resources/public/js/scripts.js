$(document).ready(function() {

  $("input:radio[name='add-animal-type']").click(function () {

    if($(this).attr('id') == 'add-animal-type-endangered') {
      $('#add-animal-endangered').show();
    } else {
      $('#add-animal-endangered').hide();
    }

  });

  $("input:radio[name='add-sighting-type']").click(function() {

    if($(this).attr('id') == 'add-sighting-type-endangered') {
      $('#add-sighting-non').hide();
      $('#add-sighting-endangered').show();
    } else {
      $('#add-sighting-endangered').hide();
      $('#add-sighting-non').show();
    }

  });

});
