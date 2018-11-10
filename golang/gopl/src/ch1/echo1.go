package main

import (
	"strings"
)

// E1 使用普通for循环
func E1(args []string) (s string) {
	sep := " "
	for i := 0; i < len(args); i++ {
		s += sep + args[i]
		sep = " "
	}
	return s
}

// E2 采用range
func E2(args []string) (s string) {
	sep := " "
	for _, arg := range args {
		s += sep + arg
		sep = " "
	}
	return s
}

// E3 采用strings包中的Join方法
func E3(args []string) (s string) {
	return strings.Join(args, " ")
}

// func main() {
// 	s1 := E1(os.Args)
// 	fmt.Println(s1)

// 	s2 := E2(os.Args)
// 	fmt.Println(s2)

// 	s3 := E3(os.Args)
// 	fmt.Println(s3)
// }
