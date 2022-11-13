import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import { Box, Button, Flex, Spacer, Text, useColorMode } from "@chakra-ui/react";
import { NavLink } from "react-router-dom";

const Navigator = (): JSX.Element => {
  return (
    <>
      <Box mb={10} bg={"black"} height={50} fontFamily={"serif"} p={1}>
        <Flex minWidth={"max-content"} alignItems={"center"} gap={5}>
          <LinkItem to="/" name="Home" />
          <LinkItem to="/about" name="About" />
          <LinkItem to="/daliy" name="Daliy" />
          <LinkItem to="/posts" name="Posts" />
          <LinkItem to="/login" name="Login" />
          <Spacer />
          <Profile />
          <DarkMode />
        </Flex>
      </Box>
    </>
  );
};

interface ILinkItemProps {
  to: string;
  name: string;
}
const LinkItem = ({ to, name }: ILinkItemProps): JSX.Element => {
  return (
    <>
      <NavLink to={to}>
        <Text color="white" ml={5} fontSize={20} >
          {name}
        </Text>
      </NavLink>
    </>
  );
};

const DarkMode = (): JSX.Element => {
  const { colorMode, toggleColorMode } = useColorMode();
  return (
    <Button w={50} onClick={toggleColorMode}>
      {colorMode === "light" ? <MoonIcon /> : <SunIcon />}
    </Button>
  );
};

const Profile = (): JSX.Element => {
  return (
    <Button w={50}>
      <span className="material-symbols-outlined">person</span>
    </Button>
  );
};

export default Navigator;
