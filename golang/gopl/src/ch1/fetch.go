package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"os"
	"syscall"
)

// Fetch is a file test
func Fetch() {
	for _, url := range os.Args[1:] {
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: %v\n", err)
			os.Exit(1)
		}
		b, err := ioutil.ReadAll(resp.Body)
		resp.Body.Close()
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: reading %s: %v\n", url, err)
			os.Exit(1)
		}
		fmt.Printf("%s", b)
	}
}

// Fetch2File used to test writing response from http.Get to file
func Fetch2File() {
	for _, url := range os.Args[1:] {
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: %v\n", err)
			os.Exit(1)
		}
		// b, err := ioutil.ReadAll(resp.Body)
		out := os.NewFile(uintptr(syscall.Stdin), "a.html")
		defer out.Close()

		_, cerr := io.Copy(out, resp.Body)
		if cerr != nil {
			fmt.Fprintf(os.Stderr, "Copy error: %v\n", cerr)
			os.Exit(1)
		}

		defer resp.Body.Close()
	}
}

func main() {
	// Fetch()
	Fetch2File()
}
