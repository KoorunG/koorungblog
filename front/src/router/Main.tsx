import { Container, Heading } from "@chakra-ui/react";
import { useEffect } from "react";
import { useRecoilState } from "recoil";
import { userState } from "../global/State";
import { IUser } from "../global/Types";

const Main = () : JSX.Element => {

  // useState와 비슷한 모양...
  const [user, setUser] = useRecoilState<IUser>(userState);

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
