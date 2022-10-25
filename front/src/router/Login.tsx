import axios from 'axios'
import { Container } from 'react-bootstrap'

const Login = () => {
  
  const loginFunc = () => {
    axios.post("").then().catch();
  }

  return (
    <>
      <Container>
        <h3 className='p-3'>Login</h3>
        <br />
        <form onSubmit={loginFunc}>
        ID : <input type={'text'}></input> <br/>
        PW : <input type={'password'}></input>
        </form>
      </Container>
    </>
  )
}

export default Login