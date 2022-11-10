// import { Nav, Navbar } from 'react-bootstrap'
// import Container from 'react-bootstrap/Container'

import { MoonIcon } from "@chakra-ui/icons";
import {
  Box,
  Button,
  Flex,
  Link,
  Spacer,
  useColorMode
} from "@chakra-ui/react";

const Navigator = () => {
  return (
    <>
      <Box mb={10} bg={"black"} height={50} fontFamily={"serif"} p={1}>
        <Flex minWidth={"max-content"} alignItems={"center"} gap={10}>
          <Link href="/" color="white" ml={5} fontSize={25}>
            Home
          </Link>
          <Link href="/about" color="white" fontSize={20}>
            About
          </Link>
          <Link href="/daliy" color="white" fontSize={20}>
            Daliy
          </Link>
          <Link href="/posts" color="white" fontSize={20}>
            Posts
          </Link>
          <Link href="/login" color="white" fontSize={20}>
            Login
          </Link>
          <Spacer />
          <DarkMode />
        </Flex>
      </Box>
    </>
  );
};

function DarkMode() {
  const { toggleColorMode } = useColorMode();
  return (
    <header>
      <Button onClick={toggleColorMode}>
        <MoonIcon />
      </Button>
    </header>
  );
}

export default Navigator;
