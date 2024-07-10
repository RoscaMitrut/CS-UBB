using ApiTest;

var client = new RestTest();

await client.Delete("11");

var flights = await client.GetAll();

for (int i = 0; i < flights.Length; i++)
{
    Console.WriteLine(flights[i].obiectivVizitat);
}

var flight = await client.GetById(2);
Console.WriteLine(flight.obiectivVizitat);

/*
var newFlight = new Excursie("TEMP OBIECTIV", DateTime.Now, "TEMP FIRMA", 111.111, 111);
var createdFlight = await client.Create(newFlight);
Console.WriteLine(createdFlight.obiectivVizitat);
*/
// await client.Update(newFlight);