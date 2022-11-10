import { Button, Container, Heading, Input, InputGroup, InputRightElement } from "@chakra-ui/react";
import axios from "axios";
import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CustomModal from "../components/CustomModal";

const Login = () => {
  // 로그인 post 요청을 보내는 함수
  const loginFunc = () => {
    axios.post("").then().catch();
  };

  const navigate = useNavigate();

  const [showModal, setShowModal] = useState(false);

  const handleShow = () => setShowModal(true);
  const handleClose = () => setShowModal(false);


  return (
    <>
      <Container>
        <Heading as={'h3'} size={'lg'}>로그인</Heading>
        <br />
        <form onSubmit={loginFunc}>
          ID : <Input placeholder='Enter your Id' />
          PW : <PasswordInput/>
        </form>

          <Button onClick={() => {
            const isGuestLogin = window.confirm('게스트 로그인을 진행하시겠습니까?');
            if(isGuestLogin) {
              window.alert('게스트 로그인이 완료되었습니다');
              navigate('/');
            }
            
          }}>게스트 로그인</Button>
          <br/><br/>
          <Button variant="primary" onClick={handleShow}>커스텀 모달창</Button>
      </Container>
      <CustomModal showModal={showModal} handleClose={handleClose} modalTitle="게스트로그인" modalBody="게스트 로그인을 진행하시겠습니까?"/>
    </>
  );
};


function PasswordInput() {
  const [show, setShow] = React.useState(false)
  const handleClick = () => setShow(!show)

  return (
    <InputGroup size='md'>
      <Input
        pr='4.5rem'
        type={show ? 'text' : 'password'}
        placeholder='Enter password'
      />
      <InputRightElement width='4.5rem'>
        <Button h='1.75rem' size='sm' onClick={handleClick}>
          {show ? 'Hide' : 'Show'}
        </Button>
      </InputRightElement>
    </InputGroup>
  )
}


export default Login;
