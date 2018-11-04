package models

import (
	"fmt"
	"math/rand"
)

// User model
type User struct {
	ID       int    `form:"id" json:"id" xml:"id"`
	UserName string `form:"username" json:"username" xml:"username" binding:"required"`
	Password string `form:"password" json:"password" xml:"password" binding:"required"`
}

// Add a user
func (u *User) Add() (id int64, err error) {
	fmt.Printf("UserName: %s, Password: %s will be added", u.UserName, u.Password)
	fmt.Println()
	id = rand.Int63()
	err = nil
	return
}
