package main
// import "os"
import (
	"fmt"
	"flag"
	"go-rpio"
)

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
	var relay = rpio.Pin(26)

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
}
