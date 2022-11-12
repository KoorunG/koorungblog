import {
  Box, Container,
  Heading,
  Input
} from "@chakra-ui/react";
import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { BASE_URL } from "../global/Constants";
import CustomModal from "../components/CustomModal";
import { PasswordInput } from "../components/PasswordInput";

const Login = () => {
  const [loginId, setLoginId] = useState("");
  const [password, setPassword] = useState("");

  // 로그인 post 요청을 보내는 함수
  const loginFunc = async () => {
    // #1. 에러처리
    const login = await axios
      .post(BASE_URL + "/login", { loginId, password })
      .catch((err) => {
        const { errorCode, message } = err.response.data;
        window.alert(`${message} ::: [${errorCode}]`);
      });

    // #2. catch에서 잡히지 않을 경우
    if (login) {
      const { username, role } = login.data;
      window.alert(`${username}님 환영합니다! :: 권한 [${role}]`);
      navigate("/");
    }
  };

  const join = () => {
    navigate('/join');
  }

  const handleLoginId: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setLoginId(e.currentTarget.value);
  };

  const handlePassword: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setPassword(e.currentTarget.value);
  };

  const navigate = useNavigate();

  return (
    <>
      <Container>
        <Heading as={"h3"} size={"lg"}>
          로그인
        </Heading>
        <br />
        <Box>
          ID :{" "}
          <Input placeholder="Enter your Id" mb={3} onChange={handleLoginId} />
          PW :{" "}
          <PasswordInput
            placeholder="Enter password"
            onChange={handlePassword}
          />
          <CustomModal
            buttonName="로그인"
            modalTitle="로그인"
            modalBody="로그인을 진행하시겠습니까?"
            colorScheme="yellow"
            confirmFunc={loginFunc}
          />
          <CustomModal
            buttonName="회원가입"
            modalTitle="회원가입"
            modalBody="회원가입을 진행햐시겠습니까?"
            colorScheme="red"
            confirmFunc={join}
          />
        </Box>
      </Container>
    </>
  );
};



export default Login;
