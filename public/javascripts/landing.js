// Mostly vanilla javascript that handles the dynamic content on the landing page of our web application

// Clears the Whale table
function clearWhaleTable(){
    $('#whaleTable tbody').remove();
}

// Dynamically generates the observation table using the json object passed to the view
function updateObservationTable() {
    var table = document.getElementById("obsTable")
    var tbody = document.createElement('tbody');
    for (var i = 0; i < obsData.length; i++) {
        let id = obsData[i]['id'];
        var tr = document.createElement('tr');
        var td = document.createElement('td');
        var button = document.createElement('button');
        button.innerHTML = id;
        button.type = button;
        tr.onclick = function () {
            updateWhaleTableFromObservation(id);
            x = tbody.getElementsByTagName("tr");
            for (var i = 0; i < x.length; i++) { x[i].style.backgroundColor = "#bab";}
            this.style.backgroundColor = '#bdb';
        }
        td.appendChild(document.createTextNode(obsData[i]['id']))
        tr.appendChild(td);

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(obsData[i]['longitude']))
        tr.appendChild(td);

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(obsData[i]['latitude']))
        tr.appendChild(td);

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(obsData[i]['dateString']))
        tr.appendChild(td);
        tbody.append(tr)
    }
    table.appendChild(tbody);

    
}

// Dynamically generates the whales table using the json object passed to the view, or a filtered subset
function updateWhaleTable(whales){
    clearWhaleTable(); 

    var table = document.getElementById("whaleTable")
    var tbody = document.createElement('tbody');
    for (var i = 0; i < whales.length; i++) {
        var tr = document.createElement('tr');

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(whales[i]['id']))
        tr.appendChild(td);

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


// Filtering functions
// These functions take the given json object and transform it based on some criteria
// This filtered subset is then passed to the dynamic table generator to be displayed
function updateWhaleTableFromObservation(j) {
    var result = obsData.find(x => x.id === j).whales;
    updateWhaleTable(result);
    document.getElementById("whaleHeader").innerText = "Whales for Observation ID # " + j
}

function showAllWhales() {
    updateWhaleTable(whalesData);
    document.getElementById("whaleHeader").innerText = "All whales in database";
}

function updateWhaleTableFromTypeFilter(){
    var result = []
    var filterVal = document.getElementById("whaleSelect").value;

    // setting selected observation to none
    x = document.getElementById("obsTable").getElementsByTagName("tr");
    for (var i = 0; i < x.length; i++) { x[i].style.backgroundColor = "#bab";}

    if(filterVal == "ANY"){
        updateWhaleTable(whalesData);
        document.getElementById("whaleHeader").innerText = "All whales in database:";
    }

    else {
        for(var i = 0; i < obsData.length; ++i){
            for(var j = 0; j < obsData[i].whales.length; ++j) {
                let focus = obsData[i].whales[j];
                if(focus.species == filterVal)
                {
                    result.push(focus);
                }
            }
        }

        updateWhaleTable(result);
        document.getElementById("whaleHeader").innerText = "All whales of type: " + filterVal;
    }




}

function updateWhaleTableFromDateFilter(){
    var result = []
    var filterVal = document.getElementById("date").value;
    console.log(filterVal)

    for(var i = 0; i < obsData.length; ++i){
        for(var j = 0; j < obsData[i].whales.length; ++j) {
            let focus = obsData[i];
            if(focus.dateString === filterVal)
            {
                result.push(focus.whales[j]);
            }
        }
    }

    updateWhaleTable(result);
    document.getElementById("whaleHeader").innerText = "All whales seen on date: " + filterVal
}


// Entry point
function initialize() {
    updateObservationTable();
    showAllWhales();
 }


window.onload = initialize
