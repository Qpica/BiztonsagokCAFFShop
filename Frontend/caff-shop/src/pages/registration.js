import React, { Component } from 'react';
import '../css/registration.css';
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBCard, MDBCardBody, MDBInput } from 'mdb-react-ui-kit';


const Registration = () => {
  return (
    <div className="registration container">
      <MDBContainer fluid>
        <MDBRow className='d-flex justify-content-center align-items-center h-100'>
          <MDBCol col='12'>

            <MDBCard className='bg-white my-5 mx-auto' style={{ borderRadius: '1rem', maxWidth: '500px' }}>
              <MDBCardBody className='p-5 w-100 d-flex flex-column'>

                <h2 className="fw-bold mb-2 text-center">Registration</h2>
                <p className="text-white-50 mb-3">Please enter your personal data!</p>
                  <div className="grey-text">
                    <MDBInput label="Your name" icon="user" group type="text" validate error="wrong"
                      success="right" />
                    <MDBInput label="Your email" icon="envelope" group type="email" validate error="wrong"
                      success="right" />
                    <MDBInput label="Confirm your email" icon="exclamation-triangle" group type="text" validate
                      error="wrong" success="right" />
                    <MDBInput label="Your password" icon="lock" group type="password" validate />
                  </div>
                <MDBBtn size='lg'>
                  Register
                </MDBBtn>
                <hr className="my-4" />
              </MDBCardBody>
            </MDBCard>

          </MDBCol>
        </MDBRow>

      </MDBContainer>
    </div>
  );
}

export default Registration;