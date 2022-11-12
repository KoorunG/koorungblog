import {
  Box,
  Button,
  Center,
  Container,
  Heading,
  Input,
  VStack,
} from "@chakra-ui/react";
import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PasswordInput } from "../components/PasswordInput";
import { BASE_URL } from "../global/Constants";

interface JoinData {
  id: string;
  name: string;
  password: string;
  email?: string;
  city?: string;
  street?: string;
  zipCode?: string;
}

const Join = () => {
  const [joinData, setJoinData] = useState<JoinData>({
    id: "",
    name: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleLoginId: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setJoinData({ ...joinData, id: e.currentTarget.value });
  };

  const handlePassword: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setJoinData({ ...joinData, password: e.currentTarget.value });
  };

  const handleName: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setJoinData({ ...joinData, name: e.currentTarget.value });
  };

  const joinFunc = async () => {
    const join = await axios
      .post(BASE_URL + "/members", joinData)
      .catch((err) => {
        const { errorCode, message, validation } = err.response.data;
        
        const errList = [];

        for(const key in validation) {
            errList.push(`${key} -> ${validation[key]}`);
        }

        const errStr : string = errList.join('\n');

        window.alert(`${message} ::: [${errorCode}] \n[${errStr}]`);
      });

    if (join) {
      const id : number = join.data;
      console.log(`식별자 ::: ${id}`);
      window.alert(`${joinData.name}님 가입을 환영합니다!`);
      navigate('/');
    }
  };

  return (
    <>
      <Container>
        <Heading as="h3">회원가입</Heading>
        <VStack>
          <Box>
            ID :{" "}
            <Input
              placeholder="Enter your Id"
              mb={3}
              onChange={handleLoginId}
            />
            PW :{" "}
            <PasswordInput
              placeholder="Enter password"
              onChange={handlePassword}
            />
            Name :{" "}
            <Input
              placeholder="이름을 입력하세요."
              mb={3}
              onChange={handleName}
            />
          </Box>
          <Box onClick={joinFunc}>
            <Button colorScheme={"facebook"}>회원가입</Button>
          </Box>
        </VStack>
      </Container>
    </>
  );
};

export default Join;
