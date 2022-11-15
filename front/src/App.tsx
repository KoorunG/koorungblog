import { ChakraProvider } from "@chakra-ui/react";
import "./App.css";
import AppRouter from "./router/AppRouter";
import { RecoilRoot } from "recoil";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <ChakraProvider>
          <AppRouter />
        </ChakraProvider>
      </RecoilRoot>
    </QueryClientProvider>
  );
};

export default App;
