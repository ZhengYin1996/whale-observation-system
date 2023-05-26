// Mostly vanilla javascript that handles the dynamic aspects of the "Add Observation" view in our web application

// Clears the table tag
function clearWhaleTable(){
    $('#whaleTable tbody').remove();
}

// Updates
function updateWhaleTable(whales){
    document.getElementById("obsCell").style.display = "block";
    clearWhaleTable(); 

    var table = document.getElementById("whaleTable")
    var tbody = document.createElement('tbody');
    for (var i = 0; i < whales.length; i++) {
        var tr = document.createElement('tr');
        var td = document.createElement('td');
        td.appendChild(document.createTextNode(whales[i]['species']))
        tr.appendChild(td);

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(whales[i]['weight']))
        tr.appendChild(td);

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(whales[i]['gender']))
        tr.appendChild(td);
        tbody.append(tr)
    }
    table.appendChild(tbody);
}


function initialize() {
    

    updateWhaleTable(whales);
    if (whales.length === 0) {
        document.getElementById("obsCell").style.display = "block";
    }
}


window.onload = initialize
