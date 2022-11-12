import { Container, Heading } from "@chakra-ui/react";
import { useEffect } from "react";
import { IUser } from "../global/Types";

interface IMainProps {
  user : IUser;
}

const Main = ({user} : IMainProps) => {

  useEffect(() => {
    if(user != null) console.table(user);
  }, []);
  
  return (
    <>
      <Container>
        <Heading as={'h3'} size={'lg'}>메인페이지</Heading>
      </Container>
    </>
  );
};

export default Main;
