package main
// import "os"
import (
	"fmt"
	"flag"
	"go-rpio"
	"net/http"
)

const webdir string = "firepi/"
const firePlaceIOPin int = 26

var relay rpio.Pin

func main() {
	var command string
	var on, off, status, server bool = false, false, false, false
	flag.StringVar(&command, "Command", "status", "Should be one of on, off or status")
	flag.BoolVar(&on, "on", false, "Turn fireplace on")
	flag.BoolVar(&off, "off", false, "Turn fireplace off")
	flag.BoolVar(&status, "status", false, "Request fireplace status")
	flag.BoolVar(&server, "server", false, "Start the webserver")
	flag.Parse()

	fmt.Println("Command =", command)

	defer rpio.Close()
	rpio.Open()
	relay = rpio.Pin(firePlaceIOPin)

	relay.Output()

	if on {
		fmt.Println("Turn On?")
		relay.High()
	}
	if off {
		fmt.Println("Turn Off?")
		relay.Low()
	}
	if status {
		fmt.Println("status?")
		fmt.Println("Current State=", relay.Read())
	}
	if server {
		fmt.Println("Current State=", relay.Read())
		http.HandleFunc(webdir + "ON", fireOn)
		http.HandleFunc(webdir +"OFF", fireOff)
		http.HandleFunc(webdir +"STATUS", fireStatus)
		err := http.ListenAndServe(":8080",nil)
		if err != nil {
			panic("Error starting server" + err.Error())
		}
	}
}

func fireOn (w http.ResponseWriter, r *http.Request) {
	relay.High()
	fmt.Fprintf(w, "On")
}

func fireOff (w http.ResponseWriter, r *http.Request) {
	relay.Low()
	fmt.Fprintf(w, "Off")
}

func fireStatus (w http.ResponseWriter, r *http.Request) {
	var stat string = "OFF"
	if relay.Read() > 0 {
		stat = "ON"
	}
	fmt.Fprintf(w, "Status = " + stat )
}