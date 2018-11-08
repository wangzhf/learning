package main

import (
	"fmt"
	"testing"
)

// cmd: go test
func TestE1(t *testing.T) {
	s := []string{"1", "2", "3", "4", "4", "5", "5", "6", "6"}
	o := E1(s)
	fmt.Println(o)
}
