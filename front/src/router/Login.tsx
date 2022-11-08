import axios from "axios";
import { useState } from "react";
import { Button, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import CustomModal from "../components/CustomModal";

const Login = () => {
  // 로그인 post 요청을 보내는 함수
  const loginFunc = () => {
    axios.post("").then().catch();
  };

  const navigate = useNavigate();

  const [showModal, setShowModal] = useState(false);

  const handleShow = () => setShowModal(true);
  const handleClose = () => setShowModal(false);


  return (
    <>
      <Container>
        <h3 className="p-3">Login</h3>
        <br />
        <form onSubmit={loginFunc}>
          ID : <input type={"text"}></input> <br />
          PW : <input type={"password"}></input>
        </form>

          <Button onClick={() => {
            const isGuestLogin = window.confirm('게스트 로그인을 진행하시겠습니까?');
            if(isGuestLogin) {
              window.alert('게스트 로그인이 완료되었습니다');
              navigate('/');
            }
            
          }}>게스트 로그인</Button>
          <br/><br/>
          <Button variant="primary" onClick={handleShow}>커스텀 모달창</Button>
      </Container>
      <CustomModal showModal={showModal} handleClose={handleClose} modalTitle="게스트로그인" modalBody="게스트 로그인을 진행하시겠습니까?"/>
    </>
  );
};


export default Login;
