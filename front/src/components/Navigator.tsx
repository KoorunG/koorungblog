import React from 'react'
import { Nav, Navbar } from 'react-bootstrap'
import Container from 'react-bootstrap/Container'
import { NavLink } from 'react-router-dom'
import { Link } from 'react-router-dom'

const Navigator = () => {
  return (
    <>
        <Navbar bg="dark" variant="dark">
        <Container>
          <Navbar.Brand href="/main">Koorungblog</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="/login">Login</Nav.Link>
            <Nav.Link href="/postsForm">Post</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  )
}

export default Navigator