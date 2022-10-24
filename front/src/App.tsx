import { Container } from 'react-bootstrap';
import './App.css';
import PostList from './components/PostList';

const App : React.FC = () => {
  return (
    <Container className="p-2">
      <PostList/>
    </Container>
  );
}

export default App;
