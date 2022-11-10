import { ChakraProvider } from '@chakra-ui/react';
import './App.css';
import AppRouter from './router/AppRouter';

const App : React.FC = () => {
  return (
    <ChakraProvider>
      <AppRouter/>
    </ChakraProvider>
  );
}

export default App;
