import React from 'react';
import '../css/login.css';
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBRow,
  MDBCol,
  MDBIcon,
  MDBInput,
  MDBDropdown, MDBDropdownItem,MDBDropdownToggle, MDBDropdownMenu, 
}
  from 'mdb-react-ui-kit';




function Login() {
  return (
    <div className="login">

      <MDBContainer className="my-5">

        <MDBCard>
          <MDBRow className='m-0'>

            <MDBCol md='6'>
              <MDBCardImage src='https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img1.webp' alt="login form" className='rounded-start w-100' />
            </MDBCol>

            <MDBCol md='6'>
              <MDBCardBody className='d-flex flex-column'>

               

                <h5 className="fw-normal my-4 pb-3" style={{ letterSpacing: '1px' }}>Sign into your account</h5>

                <MDBInput wrapperClass='mb-4' label='Email address' id='formControlLg' type='email' size="lg" />
                <MDBInput wrapperClass='mb-4' label='Password' id='formControlLg' type='password' size="lg" />

                <MDBBtn className="mb-4 px-5" color='dark' size='lg'>Login</MDBBtn>
                
                <br />
                <MDBBtn className="mb-4 px-5" color='light' size='lg'>Register</MDBBtn>
                <MDBRow className='pb-3 mt-20'>
                  <div className='d-flex flex-row justify-content-start'>
                    <a href="#!" className="small text-muted">Terms of use.</a>
                    <a href="#!" className="small text-muted">Privacy policy</a>
                  </div>
                </MDBRow>

                

              </MDBCardBody>
            </MDBCol>

          </MDBRow>
        </MDBCard>

      </MDBContainer>
    </div>
  );
}

export default Login;