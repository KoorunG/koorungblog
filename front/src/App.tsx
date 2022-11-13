import { ChakraProvider } from "@chakra-ui/react";
import "./App.css";
import AppRouter from "./router/AppRouter";
import { RecoilRoot } from "recoil";

const App: React.FC = () => {
  return (
    <RecoilRoot>
      <ChakraProvider>
        <AppRouter />
      </ChakraProvider>
    </RecoilRoot>
  );
};

export default App;
