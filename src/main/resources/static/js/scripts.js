startUpdate = function() {

  var updateFields = $(".update");
  var playerName = getPassivePlayerName();
    var update = function(){


        updateFields.each(function(element) {
            var field = this;
            var path = "value/" + playerName + "/" + this.id;
            $.get(path, function( data ) {
                field.innerHTML = data;
             });

        });

         $.get("roll",  function( data ) {
            $("#dice1")[0].innerHTML = data.dice1;
            $("#dice2")[0].innerHTML = data.dice2;
            $("#dice3")[0].innerHTML = data.dice3;
            $("#dice4")[0].innerHTML = data.dice4;
            $("#dice5")[0].innerHTML = data.dice5;
            $("#remainingRolls")[0].innerHTML = getRemainingRollsText() +': ' +data.remainingRolls;
         });

    }
update();
setInterval(update, 1500);
}
