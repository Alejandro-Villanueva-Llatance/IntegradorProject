import React, { useState } from 'react';

const Dashboard = () => {
  const [patient] = useState({
    name: 'Kevin',
    fullName: 'Kevin Salazar',
    age: 20,
    gender: 'Masculino',
    registrationDate: '14/12/2024'
  });

  const styles = {
    container: {
      minHeight: '100vh',
      backgroundColor: '#f9fafb',
      fontFamily: 'Arial, sans-serif'
    },
    header: {
      backgroundColor: 'white',
      borderBottom: '1px solid #e5e7eb',
      boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.05)'
    },
    headerContent: {
      maxWidth: '1280px',
      margin: '0 auto',
      padding: '0 1rem',
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      height: '64px'
    },
    logo: {
      display: 'flex',
      alignItems: 'center',
      gap: '12px'
    },
    logoIcon: {
      width: '40px',
      height: '40px',
      backgroundColor: '#2563eb',
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      color: 'white',
      fontSize: '18px'
    },
    logoText: {
      fontSize: '20px',
      fontWeight: '600',
      color: '#111827',
      margin: 0
    },
    logoSubtext: {
      fontSize: '14px',
      color: '#6b7280',
      margin: 0
    },
    nav: {
      display: 'flex',
      alignItems: 'center',
      gap: '32px'
    },
    navLink: {
      display: 'flex',
      alignItems: 'center',
      gap: '8px',
      color: '#6b7280',
      textDecoration: 'none',
      fontSize: '16px',
      cursor: 'pointer'
    },
    avatar: {
      width: '40px',
      height: '40px',
      backgroundColor: '#dbeafe',
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      color: '#2563eb',
      fontWeight: '500'
    },
    main: {
      maxWidth: '1280px',
      margin: '0 auto',
      padding: '2rem 1rem'
    },
    welcome: {
      marginBottom: '2rem'
    },
    welcomeTitle: {
      fontSize: '24px',
      fontWeight: 'bold',
      color: '#111827',
      marginBottom: '8px'
    },
    welcomeSubtext: {
      color: '#6b7280'
    },
    grid: {
      display: 'grid',
      gridTemplateColumns: '2fr 1fr',
      gap: '2rem'
    },
    leftColumn: {
      display: 'flex',
      flexDirection: 'column',
      gap: '2rem'
    },
    rightColumn: {
      display: 'flex',
      flexDirection: 'column',
      gap: '2rem'
    },
    card: {
      backgroundColor: 'white',
      borderRadius: '8px',
      border: '1px solid #e5e7eb',
      boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.05)'
    },
    cardContent: {
      padding: '24px'
    },
    cardHeader: {
      display: 'flex',
      alignItems: 'center',
      gap: '12px',
      marginBottom: '16px'
    },
    cardIcon: {
      width: '32px',
      height: '32px',
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    },
    cardTitle: {
      fontSize: '18px',
      fontWeight: '600',
      color: '#111827',
      margin: 0
    },
    cardSubtext: {
      fontSize: '14px',
      color: '#6b7280',
      marginBottom: '24px'
    },
    buttonGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
      gap: '16px'
    },
    button: {
      padding: '12px 24px',
      borderRadius: '8px',
      border: 'none',
      fontWeight: '500',
      cursor: 'pointer',
      transition: 'all 0.2s',
      fontSize: '16px'
    },
    buttonPrimary: {
      backgroundColor: '#2563eb',
      color: 'white'
    },
    buttonSecondary: {
      backgroundColor: '#f3f4f6',
      color: '#374151'
    },
    recordsList: {
      display: 'flex',
      flexDirection: 'column',
      gap: '16px'
    },
    record: {
      borderLeft: '4px solid #3b82f6',
      paddingLeft: '16px',
      paddingTop: '12px',
      paddingBottom: '12px'
    },
    recordHeader: {
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: '8px'
    },
    recordDate: {
      fontSize: '14px',
      fontWeight: '500',
      color: '#111827'
    },
    recordBadge: {
      fontSize: '14px',
      backgroundColor: '#dbeafe',
      color: '#1e40af',
      padding: '4px 8px',
      borderRadius: '9999px'
    },
    tagsContainer: {
      display: 'flex',
      flexWrap: 'wrap',
      gap: '8px'
    },
    tag: {
      backgroundColor: '#f3f4f6',
      color: '#374151',
      padding: '4px 8px',
      borderRadius: '4px',
      fontSize: '14px'
    },
    statsGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
      gap: '24px'
    },
    statCard: {
      textAlign: 'center'
    },
    statValue: {
      fontSize: '24px',
      fontWeight: 'bold',
      marginBottom: '8px'
    },
    statLabel: {
      color: '#6b7280'
    },
    profileGrid: {
      display: 'flex',
      flexDirection: 'column',
      gap: '16px'
    },
    profileItem: {
      display: 'flex',
      flexDirection: 'column',
      gap: '4px'
    },
    profileLabel: {
      fontSize: '14px',
      color: '#6b7280'
    },
    profileValue: {
      fontWeight: '500',
      color: '#111827'
    },
    recommendation: {
      backgroundColor: '#eff6ff',
      borderRadius: '8px',
      padding: '16px'
    },
    recommendationContent: {
      display: 'flex',
      alignItems: 'flex-start',
      gap: '12px'
    },
    recommendationIcon: {
      width: '24px',
      height: '24px',
      backgroundColor: '#2563eb',
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      marginTop: '4px',
      flexShrink: 0
    },
    recommendationTitle: {
      fontWeight: '500',
      color: '#1e3a8a',
      marginBottom: '4px'
    },
    recommendationPriority: {
      fontSize: '14px',
      color: '#1d4ed8',
      marginBottom: '8px'
    },
    recommendationText: {
      fontSize: '14px',
      color: '#2563eb'
    }
  };

  // Funciones para los botones
  const handleRegistrarSintomas = () => {
    alert('📝 Abriendo formulario para registrar síntomas...');
  };

  const handleVerEjercicios = () => {
    alert('💪 Mostrando ejercicios recomendados...');
  };

  const handleNavClick = (section) => {
    alert(`Navegando a: ${section}`);
  };

  return (
    <div style={styles.container}>
      {/* Header */}
      <header style={styles.header}>
        <div style={styles.headerContent}>
          {/* Logo */}
          <div style={styles.logo}>
            <div style={styles.logoIcon}>⚡</div>
            <div>
              <h1 style={styles.logoText}>ParkinsonCare</h1>
              <p style={styles.logoSubtext}>Sistema de Seguimiento</p>
            </div>
          </div>

          {/* Nav */}
          <nav style={styles.nav}>
            <a href="#dashboard" style={styles.navLink} onClick={(e) => { e.preventDefault(); handleNavClick('Dashboard'); }}>
              📊 Dashboard
            </a>
            <a href="#seguimiento" style={styles.navLink} onClick={(e) => { e.preventDefault(); handleNavClick('Seguimiento'); }}>
              📈 Seguimiento
            </a>
            <a href="#educacion" style={styles.navLink} onClick={(e) => { e.preventDefault(); handleNavClick('Educación'); }}>
              📚 Educación
            </a>
          </nav>

          {/* User Avatar */}
          <div style={styles.avatar}>M</div>
        </div>
      </header>

      {/* Main Content */}
      <main style={styles.main}>
        {/* Welcome Section */}
        <div style={styles.welcome}>
          <h2 style={styles.welcomeTitle}>Bienvenido, {patient.name}</h2>
          <p style={styles.welcomeSubtext}>Su panel personal de seguimiento de Parkinson</p>
        </div>

        <div style={styles.grid}>
          {/* Left Column */}
          <div style={styles.leftColumn}>
            {/* Acciones Rápidas */}
            <div style={styles.card}>
              <div style={styles.cardContent}>
                <div style={styles.cardHeader}>
                  <div style={{...styles.cardIcon, backgroundColor: '#dbeafe'}}>
                    <span style={{color: '#2563eb'}}>⚡</span>
                  </div>
                  <h3 style={styles.cardTitle}>Acciones Rápidas</h3>
                </div>
                <p style={styles.cardSubtext}>Funciones principales del sistema</p>
                
                <div style={styles.buttonGrid}>
                  <button 
                    style={{...styles.button, ...styles.buttonPrimary}}
                    onClick={handleRegistrarSintomas}
                  >
                    📝 Registrar Síntomas
                  </button>
                  <button 
                    style={{...styles.button, ...styles.buttonSecondary}}
                    onClick={handleVerEjercicios}
                  >
                    📋 Ver Ejercicios
                  </button>
                </div>
              </div>
            </div>

            {/* Registros Recientes */}
            <div style={styles.card}>
              <div style={styles.cardContent}>
                <div style={styles.cardHeader}>
                  <div style={{...styles.cardIcon, backgroundColor: '#dcfce7'}}>
                    <span style={{color: '#16a34a'}}>📅</span>
                  </div>
                  <h3 style={styles.cardTitle}>Registros Recientes</h3>
                </div>
                <p style={styles.cardSubtext}>Sus últimos registros de síntomas</p>

                <div style={styles.recordsList}>
                  {/* Registro 1 */}
                  <div style={styles.record}>
                    <div style={styles.recordHeader}>
                      <span style={styles.recordDate}>28/8/2024</span>
                      <span style={styles.recordBadge}>3 síntomas</span>
                    </div>
                    <div style={styles.tagsContainer}>
                      <span style={styles.tag}>Temblor</span>
                      <span style={styles.tag}>Rigidez</span>
                      <span style={styles.tag}>Inestabilidad Postural</span>
                    </div>
                  </div>

                  {/* Registro 2 */}
                  <div style={styles.record}>
                    <div style={styles.recordHeader}>
                      <span style={styles.recordDate}>28/8/2024</span>
                      <span style={styles.recordBadge}>3 síntomas</span>
                    </div>
                    <div style={styles.tagsContainer}>
                      <span style={styles.tag}>Temblor</span>
                      <span style={styles.tag}>Rigidez</span>
                      <span style={styles.tag}>Bradicinesia</span>
                    </div>
                  </div>

                  {/* Registro 3 */}
                  <div style={styles.record}>
                    <div style={styles.recordHeader}>
                      <span style={styles.recordDate}>30/8/2024</span>
                      <span style={styles.recordBadge}>2 síntomas</span>
                    </div>
                    <div style={styles.tagsContainer}>
                      <span style={styles.tag}>Alteraciones del Sueño</span>
                      <span style={styles.tag}>Depresión</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Información sobre Parkinson */}
            <div style={styles.card}>
              <div style={styles.cardContent}>
                <div style={styles.cardHeader}>
                  <div style={{...styles.cardIcon, backgroundColor: '#f3e8ff'}}>
                    <span style={{color: '#9333ea'}}>📖</span>
                  </div>
                  <h3 style={styles.cardTitle}>Información sobre Parkinson</h3>
                </div>
                <p style={styles.cardSubtext}>Datos estadísticos y síntomas principales</p>

                <div style={styles.statsGrid}>
                  <div style={styles.statCard}>
                    <div style={{...styles.statValue, color: '#2563eb'}}>60 años</div>
                    <div style={styles.statLabel}>Edad Promedio de Diagnóstico</div>
                  </div>
                  <div style={styles.statCard}>
                    <div style={{...styles.statValue, color: '#16a34a'}}>60% / 40%</div>
                    <div style={styles.statLabel}>Distribución por Género</div>
                  </div>
                  <div style={styles.statCard}>
                    <div style={{...styles.statValue, color: '#9333ea'}}>3</div>
                    <div style={styles.statLabel}>Síntomas Principales</div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Columna derecha */}
          <div style={styles.rightColumn}>
            {/* Mi Perfil */}
            <div style={styles.card}>
              <div style={styles.cardContent}>
                <div style={styles.cardHeader}>
                  <div style={{...styles.cardIcon, backgroundColor: '#f3e8ff'}}>
                    <span style={{color: '#9333ea'}}>👤</span>
                  </div>
                  <h3 style={styles.cardTitle}>Mi Perfil</h3>
                </div>

                <div style={styles.profileGrid}>
                  <div style={styles.profileItem}>
                    <label style={styles.profileLabel}>Nombre</label>
                    <div style={styles.profileValue}>{patient.fullName}</div>
                  </div>
                  <div style={styles.profileItem}>
                    <label style={styles.profileLabel}>Edad</label>
                    <div style={styles.profileValue}>{patient.age} años</div>
                  </div>
                  <div style={styles.profileItem}>
                    <label style={styles.profileLabel}>Género</label>
                    <div style={styles.profileValue}>{patient.gender}</div>
                  </div>
                  <div style={styles.profileItem}>
                    <label style={styles.profileLabel}>Registro</label>
                    <div style={styles.profileValue}>{patient.registrationDate}</div>
                  </div>
                </div>
              </div>
            </div>

            {/* Recomendaciones */}
            <div style={styles.card}>
              <div style={styles.cardContent}>
                <div style={styles.cardHeader}>
                  <div style={{...styles.cardIcon, backgroundColor: '#fecaca'}}>
                    <span style={{color: '#dc2626'}}>❤️</span>
                  </div>
                  <h3 style={styles.cardTitle}>Recomendaciones</h3>
                </div>
                <p style={styles.cardSubtext}>Personalizadas para usted</p>

                <div style={styles.recommendation}>
                  <div style={styles.recommendationContent}>
                    <div style={styles.recommendationIcon}>
                      <span style={{color: 'white', fontSize: '12px'}}>🧘</span>
                    </div>
                    <div>
                      <h4 style={styles.recommendationTitle}>Técnicas de Relajación</h4>
                      <p style={styles.recommendationPriority}>Media prioridad</p>
                      <p style={styles.recommendationText}>
                        Practicar meditación o yoga para reducir ansiedad y mejorar el sueño
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
