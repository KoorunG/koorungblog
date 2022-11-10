// import { Button, Modal } from 'react-bootstrap';

interface IControlModal {
  modalTitle? : string;
  modalBody? : string;
  showModal : boolean;
  handleClose : () => void;
}

const CustomModal = ({modalTitle='디폴트타이틀', modalBody='디폴트바디', showModal, handleClose} : IControlModal) => {
  return (
    <>
      {/* <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>{modalTitle}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{modalBody}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            닫기
          </Button>
          <Button variant="primary" onClick={handleClose}>
            확인
          </Button>
        </Modal.Footer>
      </Modal> */}
    </>
  )
}

export default CustomModal