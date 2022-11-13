import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import { Box, Button, Flex, Link, Spacer, useColorMode } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

const Navigator = () : JSX.Element => {
  const navigate = useNavigate();

  return (
    <>
      <Box mb={10} bg={"black"} height={50} fontFamily={"serif"} p={1}>
        <Flex minWidth={"max-content"} alignItems={"center"} gap={5}>
          <Link
            onClick={() => {
              navigate("/");
            }}
            color="white"
            ml={5}
            fontSize={25}>
            Home
          </Link>
          <Link
            onClick={() => {
              navigate("/about");
            }}
            color="white"
            fontSize={20}>
            About
          </Link>
          <Link
            onClick={() => {
              navigate("/daliy");
            }}
            color="white"
            fontSize={20}>
            Daliy
          </Link>
          <Link
            onClick={() => {
              navigate("/posts");
            }}
            color="white"
            fontSize={20}>
            Posts
          </Link>
          <Link
            onClick={() => {
              navigate("/login");
            }}
            color="white"
            fontSize={20}>
            Login
          </Link>
          <Spacer />
          <Profile />
          <DarkMode />
        </Flex>
      </Box>
    </>
  );
};

const DarkMode = () : JSX.Element => {
  const { colorMode, toggleColorMode } = useColorMode();
  return <Button w={50} onClick={toggleColorMode}>{colorMode === "light" ? <MoonIcon /> : <SunIcon />}</Button>;
};

const Profile = () : JSX.Element => {
  return (
    <Button w={50}>
      <span className="material-symbols-outlined">person</span>
    </Button>
  );
};

export default Navigator;
