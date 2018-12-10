package main

import (
	"bufio"
	"fmt"
	"os"
)

// CountLine1 count line
func CountLine1() {
	counts := make(map[string]int)
	input := bufio.NewScanner(os.Stdin)
	for input.Scan() {
		counts[input.Text()]++
		fmt.Println(len(counts))
		if len(counts) > 2 {
			break
		}
	}

	for line, n := range counts {
		if n > 1 {
			fmt.Println("out: ")
			fmt.Printf("%d\t%s\n", n, line)
		}
	}
}

// CountLine2 read word from file
func CountLine2() {
	counts := make(map[string]int)
	files := os.Args[1:]
	if len(files) == 0 {
		countLine(os.Stdin, counts)
	} else {
		for _, arg := range files {
			f, err := os.Open(arg)
			if err != nil {
				fmt.Printf("dup: %v\n", err)
				continue
			}
			countLine(f, counts)
			f.Close()
		}
	}

	for line, n := range counts {
		if n > 1 {
			fmt.Printf("%d\t%s\n", n, line)
		}
	}
}

func countLine(f *os.File, counts map[string]int) {
	input := bufio.NewScanner(f)
	for input.Scan() {
		counts[input.Text()]++
		if len(counts) > 3 {
			break
		}
	}
}

// func main() {
// 	// CountLine1()
// 	CountLine2()
// }
